




/*





 * ObjectLab, http://www.objectlab.co.uk/open is supporting FlatPack.
 *
 * Based in London, we are world leaders in the design and development
 * of bespoke applications for the securities financing markets.


 *
 * <a href="http://www.objectlab.co.uk/open">Click here to learn more</a>


 *           ___  _     _           _   _          _
 *          / _ \| |__ (_) ___  ___| |_| |    __ _| |__



 *         | | | | '_ \| |/ _ \/ __| __| |   / _` | '_ \
 *         | |_| | |_) | |  __/ (__| |_| |__| (_| | |_) |







 *          \___/|_.__// |\___|\___|\__|_____\__,_|_.__/



 *                   |__/
 *
 *                     www.ObjectLab.co.uk










 *
 * $Id: ColorProvider.java 74 2006-10-24 22:19:05Z benoitx $
 *

 * Copyright 2006 the original author or authors.
 *



 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0




 *

 * Unless required by applicable law or agreed to in writing, software


 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the


 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.sf.flatpack;

import java.io.File;













import java.io.InputStream;
import java.io.Reader;
import java.sql.Connection;

















/**
 * @author xhensevb
 *
 */


public class DefaultParserFactory implements ParserFactory {
    private static final DefaultParserFactory INSTANCE = new DefaultParserFactory();




    public static ParserFactory getInstance() {
        return INSTANCE;
    }

    /*
     * (non-Javadoc)











     *
     * @see net.sf.flatpack.PZParserFactory#newFixedWidthParser(java.sql.Connection,
     *      java.io.File, java.lang.String)











     */



    @Override





    public Parser newFixedLengthParser(final Connection con, final File dataSource, final String dataDefinition) {
        return new DBFixedLengthParser(con, dataSource, dataDefinition);
    }









    /*
     * (non-Javadoc)
     *
     * @see net.sf.flatpack.PZParserFactory#newFixedWidthParser(java.sql.Connection,
     *      java.io.InputStream, java.lang.String)
     */








    @Override
    public Parser newFixedLengthParser(final Connection con, final InputStream dataSourceStream, final String dataDefinition) {
        return new DBFixedLengthParser(con, dataSourceStream, dataDefinition);
    }

    /*
     * (non-Javadoc)







     *
     * @see net.sf.flatpack.PZParserFactory#newParser(java.io.File,
     *      java.io.File)
     */
    @Override






    public Parser newFixedLengthParser(final File pzmapXML, final File dataSource) {



        return new FixedLengthParser(pzmapXML, dataSource);
    }



















    /*
     * (non-Javadoc)
     *
     * @see net.sf.flatpack.PZParserFactory#newParser(java.io.InputStream,
     *      java.io.InputStream)
     */
    @Override
    public Parser newFixedLengthParser(final InputStream pzmapXMLStream, final InputStream dataSourceStream) {


        return new FixedLengthParser(pzmapXMLStream, dataSourceStream);








    }

    @Override
    public Parser newFixedLengthParser(final Connection con, final Reader dataSource, final String dataDefinition) {









        return new DBFixedLengthParser(con, dataSource, dataDefinition);
    }

    @Override
    public Parser newFixedLengthParser(final Reader pzmapXMLStream, final Reader dataSource) {
        return new FixedLengthParser(pzmapXMLStream, dataSource);


    }









    /*









     * (non-Javadoc)






     *
     * @see net.sf.flatpack.PZParserFactory#newParser(java.sql.Connection,
     *      java.io.InputStream, java.lang.String, char, char, boolean)


     */
    @Override
    public Parser newDelimitedParser(final Connection con, final InputStream dataSourceStream, final String dataDefinition, final char delimiter,


            final char qualifier, final boolean ignoreFirstRecord) {
        return new DBDelimiterParser(con, dataSourceStream, dataDefinition, delimiter, qualifier, ignoreFirstRecord);




    }

    /*







     * (non-Javadoc)
     *
     * @see net.sf.flatpack.PZParserFactory#newParser(java.io.File,




     *      java.io.File, char, char, boolean)
     */









    @Override








    public Parser newDelimitedParser(final File pzmapXML, final File dataSource, final char delimiter, final char qualifier,
            final boolean ignoreFirstRecord) {




        return new DelimiterParser(pzmapXML, dataSource, delimiter, qualifier, ignoreFirstRecord);



    }

    /*



     * (non-Javadoc)
     *
     * @see net.sf.flatpack.PZParserFactory#newParser(java.io.InputStream,
     *      java.io.InputStream, char, char, boolean)
     */
    @Override
    public Parser newDelimitedParser(final InputStream pzmapXMLStream, final InputStream dataSourceStream, final char delimiter,
            final char qualifier, final boolean ignoreFirstRecord) {
        return new DelimiterParser(pzmapXMLStream, dataSourceStream, delimiter, qualifier, ignoreFirstRecord);
    }

    /*
     * (non-Javadoc)




     *
     * @see net.sf.flatpack.PZParserFactory#newParser(java.io.File, char,
     *      char)
     */
    @Override
    public Parser newDelimitedParser(final File dataSource, final char delimiter, final char qualifier) {


        return new DelimiterParser(dataSource, delimiter, qualifier, false);




    }

    /**
     * Convenience method using conventional CSV separated by , and using " for qualifier if required.
     * @param dataSourceStream



     * @return a csv parser
     */


    public static Parser newCsvParser(final InputStream dataSourceStream) {




        return INSTANCE.newDelimitedParser(dataSourceStream, ',', '"');
    }

    /**
     * Convenience method using conventional CSV separated by , and using " for qualifier if required.
     * @param dataSource
     * @return a csv parser
     */
    public static Parser newCsvParser(final Reader dataSource) {
        return INSTANCE.newDelimitedParser(dataSource, ',', '"');
    }



    /*



     * (non-Javadoc)
     *
     * @see net.sf.flatpack.PZParserFactory#newParser(java.io.InputStream,



     *      char, char)
     */
    @Override
    public Parser newDelimitedParser(final InputStream dataSourceStream, final char delimiter, final char qualifier) {
        return new DelimiterParser(dataSourceStream, delimiter, qualifier, false);
    }

    @Override
    public Parser newDelimitedParser(final Connection con, final Reader dataSource, final String dataDefinition, final char delimiter,
            final char qualifier, final boolean ignoreFirstRecord) {





        return new DBDelimiterParser(con, dataSource, dataDefinition, delimiter, qualifier, ignoreFirstRecord);
    }

    @Override
    public Parser newDelimitedParser(final Reader dataSource, final char delimiter, final char qualifier) {
        return new DelimiterParser(dataSource, delimiter, qualifier, false);
    }

    @Override
    public Parser newDelimitedParser(final Reader pzmapXML, final Reader dataSource, final char delimiter, final char qualifier,
            final boolean ignoreFirstRecord) {
        return new DelimiterParser(dataSource, pzmapXML, delimiter, qualifier, ignoreFirstRecord);
    }

}
