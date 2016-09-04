package nl.avans.ivh5.example.springmvc.member;

import nl.avans.ivh5.example.springmvc.loan.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;

@Controller
// @Secured("ROLE_USER")
class MemberController {

    private MemberRepository memberRepository;
    private LoanRepository loanRepository;
    private Member member;

    @Autowired
    public MemberController(MemberRepository memberRepository, LoanRepository loanRepository) {
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
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
        // Zet de opgevraagde members in het model
        model.addAttribute("members", memberRepository.findAll());
        // Zet een 'flag' om in Bootstrap header nav het actieve menu item te vinden.
        model.addAttribute("classActiveMember","active");
        // Open de juiste view template als resultaat.
        return "views/member/list";
    }

    /**
     * Hiermee open je de create view om een nieuwe member aan te maken.
     *
     * @param member Dit object wordt aan de view meegegeven. Het object wordt gevuld met de waarden uit het formulier.
     * @param model
     * @return
     */
    @RequestMapping(value="/member/create", method = RequestMethod.GET)
    public String showCreateMemberForm(final Member member, final ModelMap model) {
        return "/views/member/create";
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
    @RequestMapping(value="/member/create", method = RequestMethod.POST)
    public String validateAndSaveMember(@Valid Member member, final BindingResult bindingResult, final ModelMap model) {
        this.member = member;
        if (bindingResult.hasErrors()) {
            return "/views/member/create";
        }
        this.memberRepository.create(member);
        model.clear();
        // return "redirect:/member";
        return "views/member";
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
        // Zet de opgevraagde uitleningen van deze member in het model
        model.addAttribute("loans", loanRepository.findAllByMemberId(id));
        // Zet een 'flag' om in Bootstrap header nav het actieve menu item te vinden.
        model.addAttribute("classActiveMember", "active");
        // Open de juiste view template als resultaat.
        return "views/member/read";
    }

}
