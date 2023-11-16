package com.workintech.bankauth.service;

import com.workintech.bankauth.entity.Member;
import com.workintech.bankauth.entity.Role;
import com.workintech.bankauth.repository.MemberRepository;
import com.workintech.bankauth.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {

    private MemberRepository memberRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(MemberRepository memberRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member register(String email, String password){
        Optional<Member> foundMember = memberRepository.findByEmail(email);
        if(foundMember.isPresent()){
            throw new RuntimeException("User with given email already exist, please login:" + email);
        }
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();

        List<Role> roleList = new ArrayList<>();
        roleList.add(userRole);

        Member member = new Member();
        member.setEmail(email);
        member.setPassword(encodedPassword);
        member.setAuthorities(roleList);
        return memberRepository.save(member);
    }

}
