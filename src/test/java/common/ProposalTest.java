package common;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ProposalTest {

    @Test
    void getMostLatestAcceptedProposal() {
        List<Proposal> proposals = new ArrayList<>();
        proposals.add(new Proposal(new Id(1, 3), null, null, null));
        proposals.add(new Proposal(new Id(1, 2), null, null, null));
        proposals.add(new Proposal(new Id(3, 1), null, null, null));
        proposals.add(new Proposal(new Id(3, 4), null, null, null));
        Proposal result = Proposal.getMostLatestAcceptedProposal(proposals);
        Proposal expect = new Proposal(new Id(3, 4), null, null, null);
        assertEquals(result, expect);
    }
}