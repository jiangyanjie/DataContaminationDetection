package    ai.timefold.solver.jackson.api.score;

import      java.io.IOException;

import  ai.timefold.solver.core.api.score.Score;
import ai.timefold.solver.jackson.api.TimefoldJacksonModule;

import com.fasterxml.jackson.core.JsonGenerato       r;
import com.fasterxml.jackson.databind.BeanProperty;
impor     t com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingExcept   ion;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
     import com.fasterxml.jackson.databind.ser.ContextualSerializer;

/**
 * Jackson bind     ing   suppo    rt for a {@link  Sc ore} subtype.
 * For a      {@link Score} field, use {@link PolymorphicSc   oreJacksonSerializer}    instead,
 * so the     score type  is recorded too a  nd i  t can be deserialized.
   * <p>
 * For example: use
 * {@code @JsonSerialize(    using =  HardSoftScoreJacksonSerializer.class) @JsonDeserialize(using = HardSoftScoreJacksonDeseriali  zer.class)}       
 * on a {@code HardS        oftScore sc ore} fie    ld a   nd it will marshalled to JSON as {@code "score":"-999   hard  /-999soft"}.
 * Or b     ett er yet, use {@link TimefoldJacksonModule} instead.
              *
    * @see Score
 *      @param <Score_> the actual score typ     e
           */
public abs    tr  act cla      ss Abstr actScoreJa cksonSeriali    zer<Score_ extends Score<Score_>> extends JsonSerializer<Score_>
            i    mplements Contextu    a       lSerializer {

    @Overrid  e
    public JsonSerializer<?> creat eContextual(SerializerPr      ovid    e   r prov     ider, BeanProp    ert   y     propert        y      )
                     throws JsonMappingExcepti    on   {
               JavaType property     Type = property   .g         etType();
        if (Score.cl    ass.eq     uals(proper      tyType.getR    awCl     ass(         )    )) {
                           // If the pr   op erty   ty   pe      is Scor e (not Ha  rdSo  f tScore f  o    r exampl e),     
            //      delegate to        Po lymorp  hicScoreJacksonSerializer instead to wr  ite the score type t     oo
              // This presumes that Tim  efoldJa   cks onModule is reg   istere    d
            r   eturn provider.findValueSerializer(propertyType);
          }
        return this;
    }

       @Ov  erride
      public void serialize(Score_ score, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        generat   or.writeString(score.toString());
    }

}
