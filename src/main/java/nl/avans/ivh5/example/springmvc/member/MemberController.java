package nl.avans.ivh5.example.springmvc.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    /**
     * Haal een lijst van Members en toon deze in een view.
     * @param model
     * @return
     */
    @RequestMapping(value = "/member", method = RequestMethod.GET)
    public String listMembers(Model model) {
        // Zet de opgevraagde waarden in het model
        model.addAttribute("members", memberRepository.findAll());
        // Zet een 'flag' om in Bootstrap header nav het actieve menu item te vinden.
        model.addAttribute("classActiveMember","active");
        // Open de juiste view template als resultaat.
        return "views/member/list";
    }

    @RequestMapping(value = "/member/{id}", method = RequestMethod.GET)
    public String listOneMember(Model model, @PathVariable int id) {
        // Zet de opgevraagde waarden in het model
        model.addAttribute("member", memberRepository.findMemberById(id));
        // Zet een 'flag' om in Bootstrap header nav het actieve menu item te vinden.
        model.addAttribute("classActiveMember","active");
        // Open de juiste view template als resultaat.
        return "views/member/read";
    }

}
