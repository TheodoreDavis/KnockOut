package hello;

import org.springframework.data.repository.CrudRepository;

public interface NameRepository extends CrudRepository<NameObj, Integer>{

}
