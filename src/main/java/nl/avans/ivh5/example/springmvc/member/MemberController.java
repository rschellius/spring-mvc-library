package nl.avans.ivh5.example.springmvc.member;

import nl.avans.ivh5.example.springmvc.loan.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Controller
public class MemberController {

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

        if (bindingResult.hasErrors()) {
            // Als er velden in het formulier zijn die niet correct waren ingevuld vinden we die hier.
            // We blijven dan op dezelfde pagina. De foutmeldingen worden daar getoond
            // (zie het create.html bestand.
            return "/views/member/create";
        }
        // Maak de member aan via de repository
        Member newMember = this.memberRepository.create(member);
        // We gaan de lijst met members tonen, met een bericht dat de nieuwe member toegevoegd is.
        // Zet de opgevraagde members in het model
        model.addAttribute("members", memberRepository.findAll());
        model.addAttribute("info", "Member '" + newMember.getFullName() + "' is toegevoegd.");
        // Zet een 'flag' om in Bootstrap header nav het actieve menu item te vinden.
        model.addAttribute("classActiveMember","active");
        // Open de juiste view template als resultaat.
        return "views/member/list";
    }

    @RequestMapping(value = "/member/{id}", method = RequestMethod.DELETE)
    public String deleteMember(Model model, @PathVariable int id) {

        // Delete de member aan via de repository
        this.memberRepository.deleteMemberById(id);
        // We gaan de lijst met members tonen, met een bericht dat de nieuwe member toegevoegd is.
        // Zet de opgevraagde members in het model
        model.addAttribute("members", memberRepository.findAll());
        model.addAttribute("info", "Member is verwijderd.");
        // Zet een 'flag' om in Bootstrap header nav het actieve menu item te vinden.
        model.addAttribute("classActiveMember","active");
        // Open de juiste view template als resultaat.
        return "views/member/list";
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

    @ExceptionHandler(value = SQLException.class)
    public ModelAndView handleError(HttpServletRequest req, SQLException ex) {
        // logger.error("Request: " + req.getRequestURL() + " raised " + ex);

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("title", "Exception in MemberController");
        mav.addObject("url", req.getRequestURL());
        // Je kunt hier kiezen in welke view je een melding toont - op een
        // aparte pagina, of als alertbox op de huidige pagina.
         mav.setViewName("error/error");
//        mav.setViewName("views/member/create");
        return mav;
    }

    /**
     * Retourneer alle members. Wordt gebruikt bij het uitlenen van een boek,
     * om een uitlening aan een member te koppelen.
     *
     * @return
     */
    public List<Member> findAllMembers() { return memberRepository.findAll(); }

}
