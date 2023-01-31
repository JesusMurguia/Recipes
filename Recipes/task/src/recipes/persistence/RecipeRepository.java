package recipes.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.business.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Recipe findById(int id);
    boolean existsById(int id);
    void deleteById(int id);
    List<Recipe> findByNameIgnoreCaseContainsOrderByDateDesc(String name);
    List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);
}