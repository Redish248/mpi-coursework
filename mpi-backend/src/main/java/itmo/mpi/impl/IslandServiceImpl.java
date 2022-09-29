package itmo.mpi.impl;

import itmo.mpi.entity.Admin;
import itmo.mpi.entity.Island;
import itmo.mpi.entity.User;
import itmo.mpi.repository.AdminRepository;
import itmo.mpi.repository.IslandRepository;
import itmo.mpi.repository.UserRepository;
import itmo.mpi.service.IslandService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IslandServiceImpl implements IslandService {

    private final IslandRepository islandRepository;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    @Override
    public List<Island> getIslands() {
        return islandRepository.findAll().stream().sorted(Comparator.comparingInt(Island::getId))
                .peek(island -> {
                    if (!checkIsVip()) {
                        island.setHasPirates(false);
                    }
                })
                .collect(Collectors.toList());
    }

    private boolean checkIsVip() {
        String nick = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByNick(nick);
        if (user != null) {
            return user.getIsVip();
        } else {
            Admin admin = adminRepository.findAdminByNick(nick);
            return admin != null;
        }
    }

}
