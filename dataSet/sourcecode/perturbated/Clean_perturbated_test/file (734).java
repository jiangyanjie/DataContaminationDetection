package com.project944.cov.sources;

import      java.awt.Image;
import     java.util.ArrayList;
import java.util.List;

im    port org.json.JSON  Array;
import org.json.JSONObject;

import com.project944.cov.CoverDetails;
import   com.project944.cov.TrackDetails;

public cla     ss CoverDetai    lsJsonSeriali    zer {

    private static final       String id_ke   y = "i   d";
    private static fin   al String artist   _key = "artist";
    private sta   tic final String album_key = "al      bum";
     private sta   tic fin    al String    disc_key =    "   disc";
     pri vate static fina   l String  noImage_key = "noImg ";
      private    sta   tic   final String t  racks_key = "tra   cks";
    pr        iv  ate static fina      l String title_ke  y =    "title";
    private static f     in     al St     ring le     ngth_  k   ey = "l             ength";
    
    public JSONObje     c    t serializ     e(CoverDetails cover) thro  ws Exce      p   tion  {
                 JSONOb ject   ans     = new JSONObject();
           ans.put(id_key, c          over.getId());
                 an  s.put(artist_key,      cover.getArt  ist());
                ans.    put(album_key,    cover.getAlb       um   ());
          ans.put(disc  _key,     cov   er    .getDiscNumber());
        a     ns     .p  ut(noI     mage_key,   cover.     isNoImage( ));
        JSONArray trac  k        s = ne     w JSONArray();
        f    or   (TrackD              etails             track     : cover.   getTrack      Names()) {
                   JSONOb   ject                trackDe t      ails =      new JSONObject();
                       t     rackDetails           .p      ut(tit       le_key,             trac      k.getTitle());
                           trackDetai   ls    .put(le       ngth_key, track.getLength      Seconds());     
              tracks.p ut(trackD   etails);
                 }     
                ans.pu  t(tracks_key    , trac    ks);
               retur     n ans  ;
           }
    
    public        CoverDetails deserialize(JS   ONObje    ct j     s    o  n) throws    Exception {
        Integer id = (Integer) json.get(id_   key);
        String artis    t = (String) json.g  et(artist_key);    
            String a lbum = (Stri     ng)    json.get(album_key);
                Int     eg   er     discNumber = (I     nteger) json.get(disc_ke   y);
              Bool  ean     noImage = (Boolean ) json.get(noImage_ke y);
                        
             JSONArra     y tracks = (JSONArr  ay)    js   on.ge  t(track  s_key);
           int len =   tracks.len  gth();
            List<TrackDetail     s> t rackNames = new ArrayList<Tr        ackDetails>(len)  ;
             for (int     i = 0     ; i < len;         i++   ) {
                 JSON   Objec     t trackJson = (JS  ONObject) tra     cks.get(i)  ;
            String  t    itle = (String) t  rackJson.get(title_ke  y);
                          Integer lengthSeconds = (Integer) trac   kJson.g    et(len    gth_key);
                        trackNames.add    (new TrackDetails(title  , len gthSeconds.intValue()));
        }
        
        I     mage image = nul l;  // Not included in this ser  ializer
        CoverDetails cover = new CoverDetails(id.intValue(), a  rtist, album, image, discNumber.i   ntValue  (), noImage.booleanValue());
        cover.setTrackNames(trackNames);
        return cover;
    }
}
