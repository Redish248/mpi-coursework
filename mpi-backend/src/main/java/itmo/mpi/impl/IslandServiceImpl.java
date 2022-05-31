package itmo.mpi.impl;

import itmo.mpi.entity.Island;
import itmo.mpi.entity.User;
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

    private final IslandRepository repository;
    private final UserRepository userRepository;

    @Override
    public List<Island> getIslands() {
        User user = getUser();
        return repository.findAll().stream().sorted(Comparator.comparingInt(Island::getId))
                .peek(island -> {
                    if (!user.getIsVip()) {
                        island.setHasPirates(false);
                    }
                })
                .collect(Collectors.toList());
    }

    private User getUser() {
        return userRepository.findByNick(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
