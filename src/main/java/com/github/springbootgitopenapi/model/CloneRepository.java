package com.github.springbootgitopenapi.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CloneRepository {

      String created_date;
      String name;
      String cloneUrl;
}
