package nl.avans.ivh5.springmvc.library.service;

import nl.avans.ivh5.springmvc.library.model.Loan;
import nl.avans.ivh5.springmvc.library.model.Member;
import nl.avans.ivh5.springmvc.library.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * Deze methode handelt een ingevuld formulier af. Als er fouten zijn opgetreden blijven we in dezelfde view.
     * Als er geen fouten waren maken we een nieuwe member en gaan we direct naar de list view voor het overzicht.
     * De nieuwe member moet dan in het overzicht staan.
     *
     * @param member De member uit het formulier. De velden van member komen uit de input velden van het formulier.
     * @return
     */
    public Member create(Member member) {
        logger.info("create - new member = " + member.getFullName());

        // Maak de member aan via de repository
        Member newMember = memberRepository.create(member);
        return newMember;
    }

    /**
     *
     * @param id
     */
    public void delete(int id) {
        logger.info("delete - member = " + id);
        this.memberRepository.deleteMemberById(id);
    }

    /**
     * Haal het member met gegeven ID uit de database en toon deze in een view.
     *
     * @param id
     * @return
     */
    public Member findMemberById(int id) {
        logger.info("findMemberById - member = " + id);
        Member result = memberRepository.findMemberById(id);
        return result;
    }

    /**
     *
     * @param id
     * @return
     */
    public ArrayList<Loan> findLoansByMemberId(int id){
        logger.info("findLoansByMemberId - member = " + id);
        return loanService.findLoansByMemberId(id);
    }

    /**
     * Retourneer alle members. Wordt gebruikt bij het uitlenen van een boek,
     * om een uitlening aan een repository te koppelen.
     *
     * @return
     */
    public List<Member> findAllMembers() {
        logger.info("findAllMembers");
        return memberRepository.findAll();
    }

}
