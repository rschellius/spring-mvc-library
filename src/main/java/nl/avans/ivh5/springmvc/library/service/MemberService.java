package nl.avans.ivh5.springmvc.library.service;

import nl.avans.ivh5.springmvc.library.model.Loan;
import nl.avans.ivh5.springmvc.library.model.Member;
import nl.avans.ivh5.springmvc.library.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    private final Logger logger = LoggerFactory.getLogger(MemberService.class);;

    private MemberRepository memberRepository;
    private LoanService loanService;
    private Member member;

    @Autowired
    public MemberService(MemberRepository memberRepository, LoanService loanService) {
        this.memberRepository = memberRepository;
        this.loanService = loanService;
    }

    /**
     * Haal een lijst van Members en toon deze in een view.
     * @param model
     * @return
     */
    public List<Member> listMembers(Model model) {
        logger.info("listMembers");
        // Zet de opgevraagde members in het model
        List<Member> result = memberRepository.findAll();
        return result;
    }

    /**
     * Deze methode handelt een ingevuld formulier af. Als er fouten zijn opgetreden blijven we in dezelfde view.
     * Als er geen fouten waren maken we een nieuwe repository en gaan we direct naar de list view voor het overzicht.
     * De nieuwe repository moet dan in het overzicht staan.
     *
     * @param member De repository uit het formulier. De velden van repository komen uit de input velden van het formulier.
     * @return
     */
    public Member create(Member member) {
        logger.info("validateAndSaveMember - new repository = " + member.getFullName());

        // Maak de repository aan via de repository
        Member newMember = memberRepository.create(member);
        return newMember;
    }

    /**
     *
     * @param id
     */
    public void delete(int id) {
        logger.info("delete repository, id = " + id);
        this.memberRepository.deleteMemberById(id);
    }

    /**
     * Haal het repository met gegeven ID uit de database en toon deze in een view.
     *
     * @param id
     * @return
     */
    public Member findMemberById(int id) {
        logger.info("delete repository, id = " + id);
        Member result = memberRepository.findMemberById(id);
        return result;
    }

    /**
     *
     * @param id
     * @return
     */
    public ArrayList<Loan> findLoansByMemberId(int id){
        logger.info("findLoansByMemberId id = " + id);
        return loanService.findLoansByMemberId(id);
    }

    /**
     *
     * @param req
     * @param ex
     * @return
     */
//    public ModelAndView handleError(HttpServletRequest req, SQLException ex) {
//        // logger.error("Request: " + req.getRequestURL() + " raised " + ex);
//
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("exception", ex);
//        mav.addObject("title", "Exception in MemberController");
//        mav.addObject("url", req.getRequestURL());
//        // Je kunt hier kiezen in welke view je een melding toont - op een
//        // aparte pagina, of als alertbox op de huidige pagina.
//         mav.setViewName("error/error");
//        return mav;
//    }

    /**
     * Retourneer alle members. Wordt gebruikt bij het uitlenen van een boek,
     * om een uitlening aan een repository te koppelen.
     *
     * @return
     */
    public List<Member> findAllMembers() { return memberRepository.findAll(); }

}