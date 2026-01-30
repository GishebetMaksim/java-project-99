package hexlet.code.service.implementations;

import hexlet.code.dto.label.LabelCreateDTO;
import hexlet.code.dto.label.LabelDTO;
import hexlet.code.dto.label.LabelUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.LabelMapper;
import hexlet.code.repository.LabelRepository;
import hexlet.code.service.interfaces.LabelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LabelServiceImpl implements LabelService {
    private final LabelRepository repository;
    private final LabelMapper mapper;

    public List<LabelDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::map)
                .toList();
    }

    public LabelDTO findById(Long id) {
        var label = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Label with id = " + id + " not found."));
        return mapper.map(label);
    }

    public LabelDTO create(LabelCreateDTO data) {
        var label = mapper.map(data);
        repository.save(label);
        return mapper.map(label);
    }

    public LabelDTO update(Long id, LabelUpdateDTO data) {
        var label = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Label with id = " + id + " not found."));
        mapper.update(data, label);
        repository.save(label);
        return mapper.map(label);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
