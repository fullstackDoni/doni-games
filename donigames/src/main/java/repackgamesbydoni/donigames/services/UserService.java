package repackgamesbydoni.donigames.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import repackgamesbydoni.donigames.model.Users;

import repackgamesbydoni.donigames.repository.RoleRepository;
import repackgamesbydoni.donigames.repository.UserRepository;




@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByEmail(username);
        if(users!=null){
            return users;
        }else{
            throw  new UsernameNotFoundException("User not found");
        }
    }
}