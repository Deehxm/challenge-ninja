package townsq.challenge.ninja.api.model;

import javax.persistence.*;

@Entity(name = "user")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long code;

    @Column(columnDefinition = "TEXT")
    private String data;

    public String getData() {
        return data;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public void setData(String data) {
        this.data = data;
    }
}
