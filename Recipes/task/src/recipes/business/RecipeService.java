package recipes.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.persistence.RecipeRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe findById(int id) {
        return recipeRepository.findById(id);
    }

    public Recipe save(Recipe toSave) {
        return recipeRepository.save(toSave);
    }

    public boolean existsById(int id){
        return recipeRepository.existsById(id);
    }

    public void deleteById(int id){
        recipeRepository.deleteById(id);
    }

    public List<Recipe> findByName(String name){
        return recipeRepository.findByNameIgnoreCaseContainsOrderByDateDesc(name);
    }

    public List<Recipe> findByCategory(String category){
        return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }

}