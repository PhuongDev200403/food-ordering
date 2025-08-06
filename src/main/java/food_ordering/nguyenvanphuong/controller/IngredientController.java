package food_ordering.nguyenvanphuong.controller;

import food_ordering.nguyenvanphuong.dto.request.IngredientCategoryCreationRequest;
import food_ordering.nguyenvanphuong.dto.request.IngredientsItemCreationRequest;
import food_ordering.nguyenvanphuong.entity.IngredientCategory;
import food_ordering.nguyenvanphuong.entity.IngredientsItem;
import food_ordering.nguyenvanphuong.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/ingredients")
public class IngredientController {

    @Autowired
    IngredientsService ingredientsService;

    @PostMapping("/ingredient_category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(
            @RequestBody IngredientCategoryCreationRequest request
//            @RequestHeader("Authorization") String token
    ) throws Exception {
        IngredientCategory ingredientCategory = ingredientsService.createIngredientCategory(request.getName(), request.getRestaurantId());

        return new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);
    }

    @PostMapping("/ingredient_item")
    public ResponseEntity<IngredientsItem> createIngredientIttem(
            @RequestBody IngredientsItemCreationRequest request
    ) throws Exception {
        IngredientsItem ingredientsItem = ingredientsService.createIngredientItem(
                request.getRestaurantId(),
                request.getName(),
                request.getCategoryId()
        );
        return new ResponseEntity<>(ingredientsItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<IngredientsItem> updateStatsIngredientsItem(@PathVariable Long id) throws Exception {
        IngredientsItem ingredientsItem = ingredientsService.updateStock(id);

        return new ResponseEntity<>(ingredientsItem, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredient(@PathVariable Long id) throws Exception {
        List<IngredientsItem> items = ingredientsService.findRestaurantsIngrendients(id);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategory(@PathVariable Long id) throws Exception {
        List<IngredientCategory> items = ingredientsService.findIngredientCategoryByRestaurantId(id);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
