package com.ead.authuser.dtos;

import com.ead.authuser.validation.UsernameConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    public interface View {
        static interface RegistrationPost {
        }

        static interface UserPut {
        }

        static interface PasswordPut {
        }

        static interface ImagePut {
        }
    }

    private UUID id;

    @JsonView(View.RegistrationPost.class)
    @NotBlank(groups = View.RegistrationPost.class)
    @Size(min = 4, max = 50, groups = View.RegistrationPost.class)
    @UsernameConstraint(groups = View.RegistrationPost.class)
    private String username;

    @JsonView(View.RegistrationPost.class)
    @NotBlank(groups = View.RegistrationPost.class)
    @Email(groups = View.RegistrationPost.class)
    @Size(max = 50, groups = View.RegistrationPost.class)
    private String email;

    @JsonView({ View.RegistrationPost.class, View.PasswordPut.class })
    @NotBlank(groups = { View.RegistrationPost.class, View.PasswordPut.class })
    @Size(min = 6, groups = View.RegistrationPost.class)
    private String password;

    @JsonView(View.PasswordPut.class)
    @NotBlank(groups = View.PasswordPut.class)
    private String oldPassword;

    @JsonView({ View.RegistrationPost.class, View.UserPut.class })
    @NotBlank(groups = { View.RegistrationPost.class, View.UserPut.class })
    @Size(max = 150, groups = { View.RegistrationPost.class, View.UserPut.class })
    private String fullName;

    @JsonView({ View.RegistrationPost.class, View.UserPut.class })
    @NotBlank(groups = { View.RegistrationPost.class, View.UserPut.class })
    @Size(max = 150, groups = { View.RegistrationPost.class, View.UserPut.class })
    private String phoneNumber;

    @JsonView({ View.RegistrationPost.class, View.UserPut.class })
    @NotBlank(groups = { View.RegistrationPost.class, View.UserPut.class })
    @Size(max = 150, groups = { View.RegistrationPost.class, View.UserPut.class })
    private String cpf;

    @JsonView(View.ImagePut.class)
    @NotBlank(groups = View.ImagePut.class)
    private String imageUrl;

}
