package com.app.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exception.PackageHandlingException;
import com.app.custom_exception.TrainerHandlingException;
import com.app.custom_exception.MemberHandlingException;
import com.app.dao.IMemberRepository;
import com.app.dao.IPackageRepository;
import com.app.dao.ITrainerRepository;
import com.app.dao.IUserRepository;
import com.app.pojos.Member;
import com.app.pojos.Package;
import com.app.pojos.Trainer;
import com.app.pojos.User;
import com.app.pojos.UserRole;

@Service
@Transactional
public class MemberServiceImpl implements IMemberService {

	@Autowired
	private IMemberRepository memberRepo;
	@Autowired
	private ITrainerRepository trainerRepo;
	@Autowired
	private IPackageRepository packageRepo;
	@Autowired
	private IUserRepository userRepo;
	@Autowired
	private IUserService userService;



	@Override
	public Member validate(Member m) {
		if (m.getPassword().equals(m.getConfirmPassword()) & m.getPassword().length() >= 5)
			return m;
		else
			throw new PackageHandlingException("invalid password entered");

	}

	@Override
	public Member addMember(Member m, int trainerId, int packageId) {
		Package p = packageRepo.findById(packageId)
				.orElseThrow(() -> new PackageHandlingException("Invalid Package id"));

		Trainer t = trainerRepo.findById(trainerId)
				.orElseThrow(() -> new TrainerHandlingException("Invalid Package id"));
		m.setAllocatedTrainer(t);
		m.setSelectedPackage(p);
		Member m1 = memberRepo.save(m);
		User u = new User(m1.getUserName(), m1.getPassword());
		u.setRole(UserRole.valueOf("MEMBER"));
		userRepo.save(u);
		System.out.println(u);
		return m1;
	}



	@Override
	public Member updateMember(Member m, int trainerId, int packageId, int MemberId) {
//		Member m=memberRepo.findById(MemberId).orElseThrow(()
//				-> new PackageHandlingException("Invalid Trainer id"));
		Trainer t = trainerRepo.findById(trainerId)
				.orElseThrow(() -> new TrainerHandlingException("Invalid Trainer id"));
		Package p = packageRepo.findById(packageId)
				.orElseThrow(() -> new PackageHandlingException("Invalid Package id"));
		m.setAllocatedTrainer(t);
		m.setSelectedPackage(p);

		return memberRepo.save(m);
	}

	@Override
	public List<Member> getAllMember() {
		return memberRepo.findAll();
	}

	@Override
	public Member getMemberById(int memberId) {
		return memberRepo.findById(memberId).orElseThrow(() -> new MemberHandlingException("Invalid member id"));
	}

	@Override
	public String deleteMember(int memberId) {
		Member m = memberRepo.findById(memberId).orElseThrow(() -> new MemberHandlingException("Invalid member id"));
		memberRepo.deleteById(memberId);
		userService.deleteUser(m.getUserName());
		return "member details deleted for ID=" + memberId;
	}

	@Override
	public Member getMemberByName(String userName) {
		Member m = memberRepo.findByName(userName);
		return m;
		}

	@Override
	public int countMembers() {
	 return	(int)memberRepo.count();
	}

}
