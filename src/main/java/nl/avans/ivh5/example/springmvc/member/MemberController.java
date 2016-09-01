package nl.avans.ivh5.example.springmvc.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
// @Secured("ROLE_USER")
class MemberController {

    private MemberRepository memberRepository;

    @Autowired
    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        // init();
    }

    /**
     * Zet waarden in de dtb als je dat wilt
     */
    private void init() {
        memberRepository.create(new Member("Robin Schellius", "r.schellius@avans.nl"));
        memberRepository.create(new Member("Jan Montizaan", "j.montizaan@avans.nl"));
        memberRepository.create(new Member("Frans Spijkerman", "f.spijkerman@avans.nl"));
    }

    @ModelAttribute("page")
    public String module() {
        return "members";
    }

    @RequestMapping("/member")
    public String listMembers(Model model) {
        // Zet de opgevraagde waarden in het model
        model.addAttribute("members", memberRepository.findAll());
        // Zet een 'flag' om in Bootstrap header nav het actieve menu item te vinden.
        model.addAttribute("classActiveMember","active");
        // Open de juiste view template als resultaat.
        return "views/member/list";
    }
}
