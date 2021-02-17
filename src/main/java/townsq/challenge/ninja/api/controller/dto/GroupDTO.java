package townsq.challenge.ninja.api.controller.dto;

import townsq.challenge.ninja.api.model.UserModel;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupDTO {

    private String type;
    private String role;
    private String idCond;
    private Map<String, String> permissions = new HashMap<>();

    public GroupDTO() {

    }

    public GroupDTO(UserModel group) {
        Map<String, String> permissions = new HashMap<>();
        List<String> pairs = new ArrayList<>();
        String[] groupData = group.getData().split(";");
        Matcher m = Pattern.compile("([\\w+,]+)").matcher(groupData[3].replace("),(",");("));
        while (m.find()) {
            pairs.add(m.group());
        }
        for (String pair : pairs)
        {
            String[] vars = pair.split(",");
            permissions.put(vars[0], vars[1]);
        }
        setType(groupData[0]);
        setRole(groupData[1]);
        setIdCond(groupData[2]);
        setPermissions(permissions);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIdCond() {
        return idCond;
    }

    public void setIdCond(String idCond) {
        this.idCond = idCond;
    }

    public Map<String, String> getPermissions() {
        return this.permissions;
    }

    public void setPermissions(Map<String, String> permissions) {
        Map<String, String> permissionsSort = new LinkedHashMap<>();
        String[] keys = {"Reservas", "Entregas", "Usuarios"};
        for (String key: keys) {
            String value = permissions.get(key);
            if(value != null && !value.isBlank()) {
                permissionsSort.put(key, value);
            }
        }
        this.permissions = permissionsSort;
    }
}