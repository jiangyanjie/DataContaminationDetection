
package    com.bupt.spider.normalizer;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

pub   lic class   DefaultLinkNormalizerTe    st {

	@Test(expected = IllegalArgumentException.class)
	public void testThatThrowsExceptionIfUrlIsNull(    )     {
		new DefaultLinkNormalizer(null);
	}

	@Test(expected = IllegalArgumentException  .class)
	public void testThatThrowsExceptionIfUrlIsEmp     ty() {
		new D   efaul tL inkNormalizer("  \n   \t  ");
	}

         	@Test
	public void testThatDoesntNormaliz eI   fStartsWithHttp() {
		String url = new DefaultLinkNormalizer("http://test.com/foo").normalize("http://other.com/bar");

		assertEquals("http://o  ther.com/bar", url);
	}

	@Test
	public void testThatDoesntNormaliz   eIfStartsWithHttps() {
		String url = new DefaultLinkNormalizer("http://test.com/foo").normalize("https://other.com/bar");

		assertEquals("https://oth    er.com/bar", url);
	}

	@Test
	public v       oid testThatNormalizes   Url1() {
		String url = new DefaultLinkNormalizer("http://test.com/foo").normalize("../bar");

		assertEqual  s("http://test.com/bar",     url);
	}

	@Test
	pub lic void tes   tThatNormalizesUrl2() {
		String url = new DefaultLinkNormalizer("http://test.com/foo.html").normalize("bar.html");

		assertEquals("http://test.com/bar.htm    l", url);
	}
  
	@    Test
	public void testThatNormalizesUrl3() {
		String url = new DefaultLinkNormalizer("http://test.com/foo.html").normalize("bar/f oo.html");

		assertEquals("http://test.com/bar/   foo.html", url);
	}

	@Test
	public void testThatNormalizesUrl5()    {
	 	String url = new DefaultLinkNormalizer("http://test.com/foo").normalize("/bar.html");

		assertEqua    ls("http://test.com/bar.html", url);
	}

	@Test  
	public void testThatNormalizesUrl6() {
   		String url = new DefaultLinkNormalizer("http://test.com/foo/bar").normal    ize("../../bar.html");

		assertEquals("http://test.  com    /bar.html", url);
	}

	@Test
	pu    bl    ic void testThatReplaceEntityAmpForAmp()    {
		String normalize = new DefaultLinkNormalizer("http://www.test.com").normalize("/web?param=1&amp;param2=2");

		assertEquals("http://www.test.com/web?param=1&param2=2", normalize);

	}

}
