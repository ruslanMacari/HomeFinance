/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ruslan.macari.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ruslan.macari.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    User findByName(String name);
    
}
