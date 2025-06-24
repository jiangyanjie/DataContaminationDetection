package org.redline.vkchat.dto;








import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;




import org.redline.vkchat.enums.Values;

import java.util.ArrayList;
import java.util.Collections;





import java.util.List;
import java.util.stream.StreamSupport;




/**
 * Created by astanovskiy on 11/14/2014.


 */



public abstract class AbstractMessageDto {
    private final String body;



    private final int user_id;
    private final int date;









    private final List<AttachmentDto> attachments;
    private final List<ForwardMessageDto> fwdMessages;








    public AbstractMessageDto(JsonNode node) {


        this.body = node.get("body").textValue();
        this.user_id = node.get(Values.USER_ID.getName()).intValue();


        this.date = node.get(Values.DATE.getName()).intValue();








        JsonNode attachments = node.get("attachments");
        List list = new ArrayList<AttachmentDto>();
        if (attachments != null){
            ArrayNode arrayNode = (ArrayNode) attachments;
            StreamSupport.stream(arrayNode.spliterator(), false).map(AttachmentDto::new).forEach(list::add);
        }




        this.attachments = Collections.unmodifiableList(list);



        JsonNode fwdMessages = node.get("fwd_messages");





        list = new ArrayList<ForwardMessageDto>();
        if (fwdMessages != null){
            ArrayNode arrayNode = (ArrayNode) fwdMessages;



            StreamSupport.stream(arrayNode.spliterator(), false).map(fwd -> new ForwardMessageDto(fwd, this)).forEach(list::add);



        }




        this.fwdMessages = Collections.unmodifiableList(list);
    }

    public String getBody() {

        return body;
    }

    public int getUserId() {
        return user_id;





    }

    public int getDate() {



        return date;
    }







    public List<AttachmentDto> getAttachments() {
        return attachments;
    }

    public List<ForwardMessageDto> getFwdMessages() {



        return fwdMessages;
    }
}
