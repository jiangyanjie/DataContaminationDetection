package com.loganalyzer.config.interfaces;

import com.loganalyzer.common.enums.OutputFileFormat;
import com.loganalyzer.common.enums.SearchType;
import com.loganalyzer.common.models.OutputConfig;
import com.loganalyzer.common.models.SearchInput;
import com.loganalyzer.outputs.IOutputRepository;
import com.loganalyzer.outputs.IReportGenerator;
import com.loganalyzer.outputs.TextfileGenerator;
import com.loganalyzer.services.ISearcher;
import com.loganalyzer.services.RangeSearchInFullFile;
import com.loganalyzer.services.SimpleTextSearch;

public class ConfigurationBuilder implements IConfiguration {
	private ISearcher searchEngine;
	private IReportGenerator reportGenerator;
	

	@Override
	public void buildConfig(SearchInput searchInput) {
		buildSearchEngine(searchInput);
		if(isSearchInputForSimpleSearch(searchInput)){
			searchEngine = new SimpleTextSearch(searchInput);
		}
		else if(isSearchInputForRangeSearch(searchInput)){
			searchEngine = new RangeSearchInFullFile(searchInput);
		}
		searchEngine.setSearchInput(searchInput);
		OutputConfig outputConfig = buildOutputConfig(searchInput);
		buildReportGenerator(searchInput,outputConfig);
		
	}

	private void buildReportGenerator(SearchInput searchInput,
			OutputConfig outputConfig) {
		// TODO Auto-generated method stub
		if(searchInput.getFileFormat().equals(OutputFileFormat.TEXTFILE)){
			reportGenerator = new TextfileGenerator();
			reportGenerator.setOutputConfig(outputConfig);
			reportGenerator.setSearchInput(searchInput);
		}
		
	}

	private void buildSearchEngine(SearchInput searchInput
			) {
		if(isSearchInputForSimpleSearch(searchInput)){
			this.searchEngine = new SimpleTextSearch();			
		}else if(isSearchInputForRangeSearch(searchInput)){
			this.searchEngine = new RangeSearchInFullFile();
		}
		searchEngine.setSearchInput(searchInput);
	}

	private OutputConfig buildOutputConfig(SearchInput searchInput) {
		OutputConfig outputConfig = new OutputConfig();
		/*
		 * outputFileLocation = "C:\\fastsearch\\output\\";
	private FilePrefix filePrefixType;
	private OutputFileFormat fileFormat;
		 */
		if(searchInput.getOutputFileLocation() !=null){
			outputConfig.setOutputFileLocation(searchInput.getOutputFileLocation());
		}
		if(searchInput.getFileFormat()!=null){
			outputConfig.setFileFormat(searchInput.getFileFormat());
		}
		if(searchInput.getPrefixType() !=null){
			outputConfig.setFilePrefixType(searchInput.getPrefixType());
		}
		return outputConfig;
	}

	private boolean isSearchInputForRangeSearch(SearchInput searchInput) {
		// TODO Auto-generated method stub
		if(searchInput.getSearchType().equals(SearchType.RANGE_SEARCH))
		return true;
		else 
			return false;
	}

	private boolean isSearchInputForSimpleSearch(SearchInput searchInput) {
		if(searchInput.getSearchType().equals(SearchType.SIMPLESEARCH))
		return true;
		else
			return false;
	}

	@Override
	public ISearcher getSearchEngine() throws Exception {
		// TODO Auto-generated method stub
		return this.searchEngine;
	}

	@Override
	public IReportGenerator getReportGenerator() throws Exception {
		// TODO Auto-generated method stub
		return this.reportGenerator;
	}

	@Override
	public IOutputRepository getOutputRepository() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
