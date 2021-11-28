package bme.hw.desk;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/desk")
public class DeskController {
    private final DeskRepository deskRepository;

    public DeskController(DeskRepository deskRepository){
        this.deskRepository = deskRepository;
    }


}
