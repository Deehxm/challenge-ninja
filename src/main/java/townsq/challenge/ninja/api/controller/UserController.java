package townsq.challenge.ninja.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import townsq.challenge.ninja.api.controller.dto.GroupDTO;
import townsq.challenge.ninja.api.controller.dto.UserDTO;
import townsq.challenge.ninja.api.model.UserModel;
import townsq.challenge.ninja.api.repository.UserRepository;

import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping(path = "/api/user/permissions/{email}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String findUserPermissionsByEmail(@PathVariable("email") String email){
        List<UserModel> userList = repository.findAllUsers();
        List<UserModel> groupList = repository.findAllGroups();
        TreeMap<String, Map<String, String>> permissions = new TreeMap<>();
        Map<String, String> permissionByRole = new LinkedHashMap<>();
        String permissionsByEmail = "";
        String idCond = "";
        if(userList.size() > 0 && groupList.size() > 0) {
            UserDTO user = getUsersByEmail(email, userList);
            if (user.getRoles() != null) {
                for (Map.Entry<String, String> role : user.getRoles().entries()) {
                    if (idCond.isBlank()) {
                        idCond = role.getKey();
                    } else if (!idCond.equalsIgnoreCase(role.getKey())) {
                        permissionByRole = new LinkedHashMap<>();
                        idCond = role.getKey();
                    }
                    GroupDTO group = getGroupByIdCondAndRole(role.getKey(), role.getValue(), groupList);
                    if (group.getPermissions() != null) {
                        for (Map.Entry<String, String> permission : group.getPermissions().entrySet()) {
                            String permissionEntry = permissionByRole.get(permission.getKey());
                            if(isMajor(permissionEntry, permission.getValue())){
                                permissionByRole.put(permission.getKey(),permission.getValue());
                            }
                        }
                        permissions.put(group.getIdCond(),permissionByRole);
                    }
                }
            }
        }
        for (String key: permissions.keySet()) {
            String result = "";
            for (Map.Entry<String, String> values: permissions.get(key).entrySet()) {
                if(!result.isBlank())
                    result = result.concat(",");
                result = result.concat("(").concat(values.getKey()).concat(",").concat(values.getValue()).concat(")");
            }
            permissionsByEmail = permissionsByEmail.concat(key.concat(";").concat("[").concat(result).concat("]").concat("\n"));
        }
        return permissionsByEmail.trim();
    }

    public UserDTO getUsersByEmail(String email, List<UserModel> userList){
        UserDTO user = new UserDTO();
        for (UserModel model: userList) {
            String[] userData = model.getData().split(";");
            if(userData[0].equalsIgnoreCase("Usuario") && userData[1].equalsIgnoreCase(email)) {
                user = new UserDTO(model);
                break;
            }
        }
        return user;
    }

    public GroupDTO getGroupByIdCondAndRole(String idCond, String role, List<UserModel> groupList){
        GroupDTO group = new GroupDTO();
        for (UserModel model: groupList) {
            String[] groupData = model.getData().split(";");
            if(groupData[0].equalsIgnoreCase("Grupo")
                    && groupData[2].equalsIgnoreCase(idCond)
                    && groupData[1].equalsIgnoreCase(role)){
                group = new GroupDTO(model);
                break;
            }
        }
        return group;
    }

    public boolean isMajor(String value, String newValue){
        if (value != null && !value.isBlank()) {
            if (newValue.equalsIgnoreCase("Escrita"))
                return true;
            else if (newValue.equalsIgnoreCase("Leitura")
                    && !value.equalsIgnoreCase("Escrita"))
                return true;
            else if (newValue.equalsIgnoreCase("Nenhuma")
                    && !value.equalsIgnoreCase("Nenhuma"))
                return false;
            else
                return false;
        } else {
            return true;
        }
    }
}