package hello.domain.income.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    private String mid;

    private Integer seq;

    public List<AttachmentsObject> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentsObject> attachments) {
        this.attachments = attachments;
    }

    private String text;
    private List<AttachmentsObject> attachments;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "mid='" + mid + '\'' +
                ", seq=" + seq +
                ", text='" + text + '\'' +
                ", attachments=" + attachments +
                '}';
    }
}
