package org.example.clothheaven.Model;

import

@Entity
@Table(name = "categories")
@Data
@NoArgConstructer
@AllArgsConstructor

public class CategoryEntity {

    @ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   public Long category_id;
   public String category_name;

}
