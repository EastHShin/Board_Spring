package com.board.easth.service;

import com.board.easth.model.Board;
import com.board.easth.model.Users;
import com.board.easth.model.repository.BoardRepository;
import com.board.easth.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    public Board save(String username, Board board){
        Users user = userRepository.findByUsername(username);
        board.setUser(user);
        return boardRepository.save(board);
    }
}
