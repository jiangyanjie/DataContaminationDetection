




package org.petrovic.mg;

import org.junit.Test;

import org.petrovic.mg.model.Artist;



import org.petrovic.mg.model.Cover;
import org.petrovic.mg.model.Song;







import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;



import static org.hamcrest.core.IsNull.notNullValue;





public class CoverTest extends BaseTest {

    @Test
    public void testCovers() throws Exception {


        List<Cover> covers = coverService.getAll();
        assertThat(covers, is(notNullValue()));
        assertThat(covers.size(), equalTo(17));
    }











    @Test










    public void testCoverBySongName() throws Exception {



        Song song = new Song();


        song.setName("Our Town");
        List<Cover> covers = coverService.bySong(song);



        assertThat(covers, is(notNullValue()));


        for (Cover cover : covers) {
            assertThat(cover.getArtistName().contains("Iris"), equalTo(true));



















        }
    }

    @Test







    public void testCoverByArtist() throws Exception {


        Artist artist = new Artist();
        artist.setId(4);
        List<Cover> covers = coverService.byArtist(artist);
        assertThat(covers, is(notNullValue()));




        for (Cover cover : covers) {



            assertThat(cover.getArtistName().contains("Buck"), equalTo(true));


        }
    }



    @Test
    public void testAddCover() throws Exception {
        Song song = songService.byName("Help Me Make It Through the Night");
        assertThat(song, is(notNullValue()));




        Artist artist = new Artist("Tammy", "Wynette");
        artist = artistService.add(artist);







        assertThat(artist, is(notNullValue()));

        Cover cover = coverService.add(new Cover(artist, song));


        assertThat(cover, is(notNullValue()));

        assertThat(cover.getArtistId(), equalTo(artist.getId()));
    }





    @Test
    public void testDeleteCover() throws Exception {
        Song song = songService.add(new Song("Ring of Fire", 1968));
        assertThat(song.getId(), is(notNullValue()));



        Artist artist = new Artist("June Carter", "Cash");
        artist = artistService.add(artist);
        assertThat(artist.getId(), is(notNullValue()));

        Cover cover = coverService.add(new Cover(artist, song));
        assertThat(cover.getId(), is(notNullValue()));
        assertThat(cover.getArtistId(), equalTo(artist.getId()));

        coverService.delete(cover);
        assertThat(coverService.exists(cover.getId()), equalTo(false));
    }
}
