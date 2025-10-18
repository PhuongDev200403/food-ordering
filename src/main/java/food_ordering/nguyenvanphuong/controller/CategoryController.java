package food_ordering.nguyenvanphuong.controller;

import food_ordering.nguyenvanphuong.dto.request.CategoryCreationResquest;
import food_ordering.nguyenvanphuong.entity.Category;
import food_ordering.nguyenvanphuong.entity.User;
import food_ordering.nguyenvanphuong.service.CategoryService;
import food_ordering.nguyenvanphuong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    //Tạo danh mục mới cho nhà hàng bởi admin
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/categories")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryCreationResquest request
                                                   //@RequestHeader("Authorization") String token
    ) throws Exception {
        //User user = userService.findUserByJwtToken(token);

        var category = categoryService.createCategory(request);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping("/categories/restaurant/{id}")
    public ResponseEntity<List<Category>> getRestaurantCategory(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token
    ) throws Exception {
        // Kiểm tra quyền nếu cần (admin/owner)
        var categories = categoryService.findCategoryByRestaurantId(id);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    //Lấy danh sách tất cả các nhà hàng bởi admin
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCate() throws Exception {
        return new ResponseEntity<>(categoryService.findAllCategory(), HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) throws Exception {
        categoryService.deleteCategoryById(id);
        return new ResponseEntity<>("Xóa thành công", HttpStatus.OK);
    }
}
