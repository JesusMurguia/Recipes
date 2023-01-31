package recipes.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import recipes.business.Recipe;
import recipes.business.RecipeService;
import recipes.business.User;
import recipes.business.UserService;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.Map;

@RestController
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @Autowired
    UserService userService;

    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<?> getRecipe(@Valid @PathVariable int id){
        if(recipeService.existsById(id)){
            return new ResponseEntity<>(recipeService.findById(id),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/api/recipe/new")
    public ResponseEntity<?> saveRecipe(@AuthenticationPrincipal UserDetails details, @Valid @RequestBody Recipe recipe) {
        String email = details.getUsername();
        Recipe createdRecipe = recipeService.save(
                new Recipe(
                        recipe.getId(),
                        recipe.getName(),
                        recipe.getDescription(),
                        recipe.getCategory(),
                        email,
                        recipe.getDate(),
                        recipe.getIngredients(),
                        recipe.getDirections()));
        return new ResponseEntity<>(Map.of("id", createdRecipe.getId()),HttpStatus.OK);
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<?> deleteRecipe(@AuthenticationPrincipal UserDetails details, @Valid @PathVariable int id){
        if(recipeService.existsById(id)){
            String recipeUser = recipeService.findById(id).getUser();
            if(recipeUser.equals(details.getUsername())){
                recipeService.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/api/recipe/{id}")
    public ResponseEntity<?> updateRecipe(@AuthenticationPrincipal UserDetails details, @Valid @PathVariable int id,@Valid @RequestBody Recipe recipe) {
        if(recipeService.existsById(id)) {
            String recipeUser = recipeService.findById(id).getUser();
            if(recipeUser.equals(details.getUsername())){
                recipeService.save(new Recipe(id,recipe.getName(),recipe.getDescription(),recipe.getCategory(),recipeUser,recipe.getDate(),recipe.getIngredients(),recipe.getDirections()));
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/recipe/search")
    public ResponseEntity<?> getRecipe(@Valid @RequestParam(name = "name", required = false) String name, @RequestParam(name = "category", required = false) String category){
        if((name != null && category != null) || (name == null && category == null)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(name != null){
            return new ResponseEntity<>(recipeService.findByName(name),HttpStatus.OK);
        }
        return new ResponseEntity<>(recipeService.findByCategory(category), HttpStatus.OK);
    }

}