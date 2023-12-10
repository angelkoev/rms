package com.rms.web;

import com.rms.model.dto.ReviewDTO;
import com.rms.service.Impl.ReviewServiceImpl;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewServiceImpl reviewServiceImpl;

    public ReviewController(ReviewServiceImpl reviewServiceImpl) {
        this.reviewServiceImpl = reviewServiceImpl;
    }

    @GetMapping("/all")
    public String viewAll() {
        return "allReviews";
    }

    @GetMapping("/add")
    public String showAddReviewForm() {

        return "addReviewForm";
    }

    @PostMapping("/add")
    String registerNewUser(@Valid ReviewDTO reviewDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("reviewDTO", reviewDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reviewDTO", bindingResult);

            return "redirect:/reviews/add";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        reviewDTO.setUsername(username);

        this.reviewServiceImpl.addReview(reviewDTO);

        return "redirect:/";
    }

    @ModelAttribute
    public ReviewDTO reviewDTO() {
        return new ReviewDTO();
    }
}
