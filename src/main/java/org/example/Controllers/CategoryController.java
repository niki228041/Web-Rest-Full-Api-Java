package org.example.Controllers;

import Dto.CategoryDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class CategoryController {

    public static List<CategoryDTO> categoryDTOS = new ArrayList<>();

    @GetMapping("/")
    public List<CategoryDTO> index(){
        return categoryDTOS;
    }

    @PostMapping("/add")
    public String createCategory(@RequestBody CategoryDTO categoryDTO){

        categoryDTOS.add(categoryDTO);
        return "added " + categoryDTO.toString();
    }

    @DeleteMapping("/remove")
    public String remove(int id)
    {
        for (var category :categoryDTOS) {
            if(category.getId() == id)
            {
                categoryDTOS.remove(category);
                return "removed " + Integer.toString(id);

            }
        }
        return "didn't found category with " + Integer.toString(id) + " id";
    }

    @PutMapping("/edit")
    public String edit(@RequestBody CategoryDTO categoryDTO)
    {

        for (var category :categoryDTOS) {
            if(category.getId() == categoryDTO.getId())
            {
                categoryDTOS.remove(category);
                categoryDTOS.add(categoryDTO);
                return "added " + categoryDTO.toString();

            }
        }
        return "not found category with " + categoryDTO.getId() + " id";

    }
}
