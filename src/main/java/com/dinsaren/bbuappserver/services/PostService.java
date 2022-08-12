package com.dinsaren.bbuappserver.services;

import com.dinsaren.bbuappserver.constants.Constants;
import com.dinsaren.bbuappserver.models.Category;
import com.dinsaren.bbuappserver.models.Post;
import com.dinsaren.bbuappserver.models.Slide;
import com.dinsaren.bbuappserver.models.User;
import com.dinsaren.bbuappserver.repository.CategoryRepository;
import com.dinsaren.bbuappserver.repository.PostRepository;
import com.dinsaren.bbuappserver.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Post> findAll(){
        List<Post> listAll =  postRepository.findAllByStatus(Constants.ACTIVE_STATUS);
        List<Post> list =new ArrayList<>();
        listAll.forEach(p->{
            Post post = new Post();
            post = p;
            Optional<User> user = userRepository.findById(Long.valueOf(p.getCreatedId()));
            if(user.isPresent()){
                post.setUsername(user.get().getUsername());
            }

            Optional<Category> category =categoryRepository.findById(p.getCategoryId());
            if(category.isPresent()){
                post.setCategoryName(category.get().getName());
            }
            list.add(post);
        });
        return list;
    }
}
