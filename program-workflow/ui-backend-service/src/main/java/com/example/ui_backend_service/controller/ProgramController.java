package com.example.ui_backend_service.controller;

import com.example.ui_backend_service.model.CommonSchemaProgram;
import com.example.ui_backend_service.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/programs")
public class ProgramController {

    @Autowired
    private ProgramService programService;

    @PostMapping
    public ResponseEntity<CommonSchemaProgram> createProgram(@RequestBody CommonSchemaProgram program, @RequestParam String programType) {
        CommonSchemaProgram savedProgram = programService.processAndSaveProgram(program, programType);
        return ResponseEntity.ok(savedProgram);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonSchemaProgram> updateProgram(@PathVariable String id, @RequestBody CommonSchemaProgram program) {
        System.out.println("URL ID: " + id);
        System.out.println("Payload Program ID: " + program.getProgramId());
        
        if (!program.getProgramId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        CommonSchemaProgram updatedProgram = programService.updateProgram(program);
        return ResponseEntity.ok(updatedProgram);
    }

    @GetMapping
    public ResponseEntity<List<CommonSchemaProgram>> getAllPrograms() {
        List<CommonSchemaProgram> programs = programService.findAllPrograms();
        return ResponseEntity.ok(programs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonSchemaProgram> getProgramById(@PathVariable String id) {
        System.out.println(programService.findProgramById(id));
        return programService.findProgramById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<CommonSchemaProgram>> getProgramsByTitle(@RequestParam String title) {
        List<CommonSchemaProgram> programs = programService.findProgramsByTitle(title);
        return ResponseEntity.ok(programs);
    }
}
