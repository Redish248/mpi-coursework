package itmo.mpi.controller;

import itmo.mpi.dto.ExpeditionOption;
import itmo.mpi.dto.ExpeditionRequest;
import itmo.mpi.service.OptionsLookUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/request")
public class ExpeditionRequestsController {

    private final OptionsLookUpService optionsLookUpService;

    @PostMapping("/options")
    public List<ExpeditionOption> getExpeditionOptions(@RequestBody ExpeditionRequest request) {
        return optionsLookUpService.lookUpOptions(request);
    }

}
