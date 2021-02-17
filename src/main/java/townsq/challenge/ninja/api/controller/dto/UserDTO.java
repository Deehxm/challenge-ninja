package townsq.challenge.ninja.api.controller.dto;

import townsq.challenge.ninja.api.model.UserModel;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class UserDTO {

    private String type;
    private String email;
    private Multimap<String, String> roles;

    public UserDTO() {

    }

    public UserDTO(UserModel user) {
        Multimap<String, String> roles = ArrayListMultimap.create();
        List<String> pairs = new ArrayList<>();
        String[] userData = user.getData().split(";");
        Matcher m = Pattern.compile("([\\w+,]+)").matcher(userData[2].replace("),(",");("));
        while (m.find()) {
            pairs.add(m.group());
        }
        for (String pair : pairs)
        {
            String[] vars = pair.split(",");
            roles.put(vars[1], vars[0]);
        }
        setType(userData[0]);
        setEmail(userData[1]);
        setRoles(roles);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Multimap<String, String> getRoles() {
        return roles;
    }

    public void setRoles(Multimap<String, String> roles) {
        this.roles = roles;
    }

}
