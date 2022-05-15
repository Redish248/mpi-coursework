package itmo.mpi.impl;

import itmo.mpi.repository.UserRepository;
import itmo.mpi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

}
