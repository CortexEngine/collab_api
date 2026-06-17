package com.example.collab.dto.response;

import java.util.List;

public record LoginResponse(

  String token, 

  String type, 
  
  List<String> roles

) {
}
