package com.rewardomain.contribution.distribution;

import org.springframework.stereotype.Component;
import com.rewardomain.contribution.bean.*;

import java.util.List;
@Component
public class Distribution {
		public Distribution() {}
		public void distribute (List<Beneficiary> beneficiaries, double reward) {
		for (Beneficiary beneficiary : beneficiaries) { beneficiary.setSavings (beneficiary.getSavings() +
		reward * (beneficiary.getPercentage() / 100.0));
		}
		}
}