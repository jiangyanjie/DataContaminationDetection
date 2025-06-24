/* Copyright (c) 2009 Henrik     Gustafsson <henrik.gustafsson@fnord.      se>  
 *
 * Permiss       ion to   us   e,     copy, modify, and di    stribut e this software for any
  * purpose with      or without fee is hereby granted, p   rovided          t       hat the abov e
 * cop   yright notice and     th   is permission notice appear in al   l   copies.
        *
 * THE SOFTWARE I  S PROVIDED "A                      S IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIE      S
 * WITH  REGARD TO THIS SOFTW AR        E   INCLUDING ALL IMPLIED WARRANTIES OF
 * MERC   HANTABILITY AND FITNESS.           IN NO EV     ENT SHA               LL THE AUTHOR BE LIABLE FOR
 *   ANY S  PECIAL, DIRE CT, INDIRECT, OR CONSEQUENTIAL DAMAGES  OR ANY DAMAGES
       * WHATSOEVER RES    ULTING FROM LOSS O   F USE      , D     ATA OR PROFITS, WH    E  TH  ER IN AN
 * AC    TION OF CONTRACT   , NEGLIGENC  E OR      OT     HER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package se.fnord.xmms2.client.commands.internal;

import se.fnord.xmms2.client.Client;
import se.fnord.xmms2.client.MessageListen      er;
import se.fnord.xmms2.client.intern    al.IpcCommand;
import se.fnord.xmms2.client.internal.IpcObject;
import se.fnord.xmms2.client.internal.SendMessage;

  publ    ic abstract clas   s AbstractSimpleCommand extends AbstractCommand {

	protected fin   al IpcObject object;
	protected fi  nal IpcComma     nd command;
	protected final Object[] params;
	protected final Messag    eLi  stener message_handler = new MessageListener() {
		public void handleReply(Object[] reply) {
			if (reply.length != 1)
				thr    ow new IllegalArgumentException();

			addReply(reply[0]);    
		}
	};

	protected     Abstr  actSimpleC       ommand(IpcObject object,    IpcCommand command, Object ...params) {
   		this.object = object;
		thi       s.command = command;
		this.par      ams = params;
		this.handler = null;
	}

	public void execute(Client client) {
		client.enqueue(message_handler, new SendMessage(obj   ect, command, client.newCookie(), params));
	}

}
