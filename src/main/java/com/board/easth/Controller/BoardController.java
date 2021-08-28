package com.board.easth.Controller;

import com.board.easth.model.Board;
import com.board.easth.model.repository.BoardRepository;
import com.board.easth.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator  boardValidator;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 1) Pageable pageable, @RequestParam(required = false, defaultValue = "") String searchText) {
//        Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
        int currentPage = boards.getPageable().getPageNumber();
        int startPage = (currentPage - (currentPage % 5)) + 1;
        int endPage = Math.min(boards.getTotalPages(),startPage + 4);
        if(endPage < 1)
            endPage = 1;
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boards", boards);
        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id) {
        if (id == null) {
            model.addAttribute("boards", new Board());
        } else {
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("boards", board);
        }

        return "board/form";
    }

    @PostMapping("/form")
    public String formSubmit(@ModelAttribute("boards") @Valid Board board, BindingResult bindingResult) {
        boardValidator.validate(board, bindingResult);
        if (bindingResult.hasErrors()) {
            return "board/form";
        }
        boardRepository.save(board);
        return "redirect:/board/list";
    }
}
