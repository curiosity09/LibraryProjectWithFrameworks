package by.tms.service.impl;

import by.tms.dao.SectionDao;
import by.tms.dto.SectionDto;
import by.tms.entity.Section;
import by.tms.mapper.impl.SectionMapper;
import by.tms.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
public class SectionServiceImpl implements SectionService {

    private final SectionDao sectionDao;
    private final SectionMapper sectionMapper = SectionMapper.getInstance();

    @Override
    public List<SectionDto> findAllSection() {
        List<Section> sections = sectionDao.findAll();
        return sectionMapper.mapToListDto(sections);
    }

    @Override
    @Transactional
    public Long addNewSection(Section section) {
        return sectionDao.save(section);
    }

    @Override
    public Optional<SectionDto> findSectionByName(String sectionName) {
        Optional<Section> optionalSection = sectionDao.findByName(sectionName);
        return Optional.of(sectionMapper.mapToDto(optionalSection.orElseGet(Section::new)));
    }

    @Override
    @Transactional
    public void updateSection(Section section) {
        sectionDao.update(section);
    }

    @Override
    @Transactional
    public void deleteSection(Section section) {
        sectionDao.delete(section);
    }

    @Override
    public boolean isSectionExist(Long id) {
        return sectionDao.isExist(id);
    }

    @Override
    public Optional<SectionDto> findSectionById(Long id) {
        Optional<Section> optionalSection = sectionDao.findById(id);
        return Optional.ofNullable(sectionMapper.mapToDto(optionalSection.orElse(null)));
    }
}