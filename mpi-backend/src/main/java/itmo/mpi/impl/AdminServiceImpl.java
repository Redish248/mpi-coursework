package itmo.mpi.impl;

import itmo.mpi.repository.AdminRepository;
import itmo.mpi.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
}
