package com.inventory.inventory_management_system.setting.repository;

import com.inventory.inventory_management_system.setting.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {
    Optional<Setting> findByKey(String key);
    List<Setting> findByGroup(String group);
    boolean existsByKey(String key);
}
