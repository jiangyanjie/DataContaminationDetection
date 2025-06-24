/*
 * Copyright 2014 Eediom Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.araqne.ahocorasick;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AhoCorasickSearch {
	private TrieNode root;
	private List<TrieNode> nodes;

	public AhoCorasickSearch() {
		this.root = TrieNode.root();
		this.nodes = new ArrayList<TrieNode>();
		this.nodes.add(root);
	}

	public void debug() {
		System.out.println(nodes.size() + " nodes");
		for (TrieNode node : nodes)
			System.out.println(node);
	}

	public AhoCorasickSearch addKeyword(String str) {
		return addKeyword(new StringKeyword(str));
	}

	public AhoCorasickSearch addKeyword(Keyword pattern) {
		byte[] keyword = pattern.getKeyword();
		TrieNode currentNode = root;

		for (int i = 0; i < keyword.length; i++) {
			byte b = keyword[i];
			if (!currentNode.hasNext(b)) {
				TrieNode node = currentNode.addNext(nodes.size(), b);
				nodes.add(node);
			}
			currentNode = currentNode.next(b);
		}
		currentNode.setTerminal(pattern);

		return this;
	}

	public void compile() {
		Queue<TrieNode> queue = new LinkedList<TrieNode>();

		queue.add(root);
		while (!queue.isEmpty()) {
			TrieNode current = queue.poll();
			current.compile();
			queue.addAll(current.getAllNexts());
		}
	}

	public List<SearchResult> search(String str) {
		return search(str.getBytes());
	}

	public List<SearchResult> search(InputStream is) throws IOException {
		return search(is, 0);
	}

	public List<SearchResult> search(InputStream is, int maxResultCount) throws IOException {
		SearchContext ctx = new SearchContext(maxResultCount);
		return search(is, ctx);
	}

	public List<SearchResult> search(InputStream is, SearchContext ctx) throws IOException {
		List<SearchResult> results = new ArrayList<SearchResult>();
		byte[] buf = new byte[1048576];
		int limit;
		while ((limit = is.read(buf)) != -1) {
			List<SearchResult> result = search(buf, 0, limit, ctx);
			results.addAll(result);
		}
		return results;
	}

	public List<SearchResult> search(byte[] buf) {
		return search(buf, 0, buf.length);
	}

	public List<SearchResult> search(byte[] buf, int offset, int limit) {
		return search(buf, offset, limit, new SearchContext());
	}

	public List<SearchResult> search(byte[] buf, SearchContext ctx) {
		return search(buf, 0, buf.length, ctx);
	}

	public List<SearchResult> search(byte[] buf, int offset, int limit, SearchContext ctx) {
		List<SearchResult> results = new ArrayList<SearchResult>();
		TrieNode node = nodes.get(ctx.getLastNodeId());
		int length = ctx.getLength();
		int nrc = ctx.getNeedResultCount();
		boolean overset = ctx.isIncludeOverlapPatterns();

		if (nrc == 0 || buf.length < offset)
			return results;

		if (offset < 0)
			throw new IllegalArgumentException("offset must not be negative");
		if (buf.length < offset + limit)
			limit = buf.length - offset;

		int searchLimit = offset + limit;
		for (int i = offset; i < searchLimit; i++) {
			node = node.step(buf[i]);
			if (overset) {
				for (Keyword p : node.getTerminals()) {
					int pos = length + i - offset - (p.length() - 1);
					results.add(new SearchResult(pos, p));
					if (--nrc == 0)
						break;
				}
			} else {
				Keyword p = node.getTerminal();
				if (p != null) {
					int pos = length + i - offset - (p.length() - 1);
					results.add(new SearchResult(pos, p));
					if (--nrc == 0)
						break;
				}
			}
		}
		ctx.setLastNodeId(node.getId());
		ctx.addLength(limit);
		ctx.addResultCount(results.size());

		return results;
	}

	private class StringKeyword implements Keyword {
		private String str;
		private byte[] keyword;
		private int length;

		public StringKeyword(String str) {
			this.str = str;
			this.keyword = str.getBytes();
			this.length = keyword.length;
		}

		@Override
		public String getName() {
			return str;
		}

		@Override
		public byte[] getKeyword() {
			return keyword;
		}

		@Override
		public int length() {
			return length;
		}
	}
}
