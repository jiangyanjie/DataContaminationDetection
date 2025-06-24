package   com.theokanning.openai.audio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import     java.util.List;      

/*       *
 * A request f    or   OpenA    i to create transcrip tion based        o    n an audio file
 * All fi  elds excep   t model are optional
 *
 * https://platform.openai.com/docs/api-reference/audio/create
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor  
@Data
pu    blic class Cre        ateTranscriptionR       eques   t {

    /**
            * Th           e name of the     mod            el to   u  se      .
     */
    @Non  Nu      ll
        String model  ;

    /**
     * An optional text to guide the model's           style   or conti     nue a prev   ious audio segment. The prompt should    m   atch the audio la    n      gua   ge.
                 */
          Strin  g  prompt;
 
      /           *   *
        * The format of t    he transcript output    , in one of these option s: json     or verbo  se_json 
          *      /
        @JsonP roperty("resp    onse  _form    at")
            St  ring responseFormat;

    /**
     * The    s   ampling temperature,    b     etween 0 and 1.
         * Higher values like  0.8 wi   ll make the output   mo               re rando  m, while lower v alues lik    e     0.2 will make                      it more foc   used a         nd deterministic.
     * If set to 0, the mode   l  will use log probability to   automatical  l    y increa       se the temperature until      cer    tain   threshold s are hi   t.
     */
    Double temp   erature;

    /**
         * The language of the input audio. Supplying the input l  an   guage in ISO  -6    39-1 format will      improve    accu    r       acy and      latency      .
             */
          String la   nguage;   

    /**
     *   The timestamp granular            ities   to popul ate for this transcription. response_for    m at must     be     set ve    rbose_json to use timesta mp granularities.<br>
          * Either or both of these options ar    e supp or    t   ed: wo     rd, or segment. <br>
     * Note: There is n   o additional latency for segme   nt timestamps, but    generating word     timestamps incurs addi   tional latency.
     */
    @JsonProperty("timestamp_granularities ")
    List<String> timestampGranularities;

}
