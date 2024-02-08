package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final HttpSession session;
    private final BoardRepository boardRepository;

    @GetMapping({"/", "/board"})
    public String index(HttpServletRequest request) {

        List<Board> boardList = boardRepository.findAll();
        request.setAttribute("boardList", boardList);

        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO requestDTO, HttpServletRequest request){

        if (requestDTO.getTitle().length() > 20) {
            request.setAttribute("status", 400);
            request.setAttribute("msg", "title 길이가 20자를 초과해서는 안돼요");
            return "error/40x";
        }
        if (requestDTO.getContent().length() > 20) {
            request.setAttribute("status", 400);
            request.setAttribute("msg", "content 길이가 20자를 초과해서는 안돼요");
            return "error/40x";
        }

        boardRepository.save(requestDTO);

        return "redirect:/";
    }

    @PostMapping("/board/{sno}/updateForm")
    public String updateForm(@PathVariable int sno, HttpServletRequest request) {

        Board board = boardRepository.findBySno(sno);
        request.setAttribute("board", board);

        return "board/updateForm";
    }

    @PostMapping("/board/{sno}/update")
    public String update(@PathVariable int sno, BoardRequest.UpdateDTO requestDTO){

        boardRepository.update(requestDTO, sno);

        return "redirect:/";
    }

    @PostMapping("/board/{sno}/delete")
    public String delete(@PathVariable int sno, HttpServletRequest request){

        boardRepository.delete(sno);

        return "redirect:/";
    }

}
