package nl.avans.ivh5.example.springmvc.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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

    /**
     * Deze methode handelt een ingevuld formulier af. Als er fouten zijn opgetreden blijven we in dezelfde view.
     * Als er geen fouten waren maken we een nieuwe member en gaan we direct naar de list view voor het overzicht.
     * De nieuwe member moet dan in het overzicht staan.
     *
     * @param member De member uit het formulier. De velden van member komen uit de input velden van het formulier.
     * @param bindingResult Het resultaat van de view.
     * @param model
     * @return
     */
    @RequestMapping(value="/memberctrl", params={"save"})
    public String saveMember(final Member member, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "/member/create";
        }
        this.memberRepository.create(member);
        model.clear();
        return "redirect:/member";
    }

    /**
     * Hiermee open je de create view om een nieuwe member aan te maken.
     *
     * @param member Dit object wordt aan de view meegegeven. Het object wordt gevuld met de waarden uit het formulier.
     * @param model
     * @return
     */
    @RequestMapping(value="/member/create")
    public String saveMember(final Member member, final ModelMap model) {
        return "/views/member/create";
    }

    /**
     * Haal het member met gegeven ID uit de database en toon deze in een view.
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/member/{id}", method = RequestMethod.GET)
    public String listOneMember(Model model, @PathVariable int id) {
        // Zet de opgevraagde waarden in het model
        model.addAttribute("member", memberRepository.findMemberById(id));
        // Zet een 'flag' om in Bootstrap header nav het actieve menu item te vinden.
        model.addAttribute("classActiveMember", "active");
        // Open de juiste view template als resultaat.
        return "views/member/read";
    }

}
