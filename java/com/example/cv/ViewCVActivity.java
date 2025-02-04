package com.example.cv;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import androidx.core.content.FileProvider;
import android.content.Intent;


import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.BuildConfig;


/** @noinspection ALL*/
public class ViewCVActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 101;
    private File pdfFile;
    private ImageView ProfileImage;
    private LinearLayout personalLayout,educationLayout, experienceLayout, awardsLayout, phdLayout,lecturesLayout;
    private LinearLayout bookLayout, bookChapterLayout, journalLayout, conferencePaperLayout,memberLayout,citationLayout;
    private LinearLayout researchInterestLayout, consultancyLayout, patentLayout,programLayout;
    private DatabaseReference databaseReference;

    private static final String TAG = "ViewCVActivity";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cv); // Ensure you have this layout file

        // Initialize layout components
        ImageButton backButton = findViewById(R.id.back_button);

        personalLayout = findViewById(R.id.personal_layout);
        ProfileImage = findViewById(R.id.faculty_image);
        experienceLayout = findViewById(R.id.experience_layout);
        educationLayout = findViewById(R.id.education_layout);
        awardsLayout = findViewById(R.id.awards_layout);
        phdLayout = findViewById(R.id.scholar_layout);
        bookLayout = findViewById(R.id.book_layout);
        bookChapterLayout = findViewById(R.id.book_chapter_layout);
        journalLayout = findViewById(R.id.journal_layout);
        conferencePaperLayout = findViewById(R.id.conference_paper_layout);
        researchInterestLayout = findViewById(R.id.research_interests_layout);
        consultancyLayout = findViewById(R.id.consultancy_layout);
        patentLayout = findViewById(R.id.patents_layout);
        memberLayout = findViewById(R.id.membership_layout);
        programLayout = findViewById(R.id.programs_layout);
        lecturesLayout = findViewById(R.id.lectures_layout);
        citationLayout = findViewById(R.id.citation_layout);
        Button downloadButton = findViewById(R.id.download_button);
        Button shareButton = findViewById(R.id.share_button);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkStoragePermission()) {
                // Permission already granted, proceed with downloading
                createAndSharePDF();
            } else {
                // Request permission
                requestStoragePermission();
            }
        } else {
            // Permission handling for versions below Android 6.0
            createAndSharePDF();
        }


        shareButton.setOnClickListener(v ->createAndSharePDF());

        downloadButton.setOnClickListener(v ->requestStoragePermission());


        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        fetchCVDetails(); // Load CV details from Firebase

        // Back Button Click
        backButton.setOnClickListener(v -> {
            finish(); // Go back to the previous activity
        });
    }

    private void fetchCVDetails() {

        loadPersonalInformation();
        fetchEducationDetails();
        fetchExperienceDetails();
        fetchAwards();
        fetchPhDs();
        fetchBooksDetails();
        fetchBookChapters();
        fetchJournals();
        fetchConferences();
        fetchResearchInterests();
        fetchConsultancies();
        fetchPatents();
        fetchMembershipDetails();
        fetchInvitedLectures();
        fetchPrograms();
        fetchCitationDetails();
    }

    private boolean checkStoragePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    // Request storage permission at runtime
    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                STORAGE_PERMISSION_CODE);
    }


    private void loadPersonalInformation() {
        databaseReference.child("faculty").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot facultySnapshot : dataSnapshot.getChildren()) {
                    Faculty faculty = facultySnapshot.getValue(Faculty.class);
                    if (faculty != null) {
                        addFacultyView(faculty);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewCVActivity.this, "Failed to load faculty details.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addFacultyView(Faculty faculty) {
        TextView facultyView = new TextView(this);
        facultyView.setText("Faculty Name: " + faculty.getName() +
                "\nDesignation: " + faculty.getDesignation() +
                "\nDepartment: " + faculty.getDepartment() +
                "\nAge: " + faculty.getAge() +
                "\nGender: " + faculty.getGender() +
                "\nContact: " + faculty.getContact() +
                "\nEmail: " + faculty.getEmail());

        facultyView.setPadding(16, 16, 16, 16);
        facultyView.setTextSize(16);
        facultyView.setBackgroundResource(android.R.color.background_light);
        facultyView.setTextColor(getResources().getColor(android.R.color.black));
        personalLayout.addView(facultyView);

    }


    private void fetchEducationDetails() {
        databaseReference.child("Education").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot educationSnapshot : dataSnapshot.getChildren()) {
                    Education education = educationSnapshot.getValue(Education.class);
                    if (education != null) {
                        Log.d("EducationData", "DegreeName: " + education.getDegreeName() +
                                ", Specialisation: " + education.getSpecialisation() +
                                ", MonthYearOfPassing: " + education.getMonthYearOfPassing() +
                                ", CollegeName: " + education.getCollegeName() +
                                ", DegreeType: " + education.getDegreeType());
                        addEducationView(education);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewCVActivity.this, "Failed to load education details.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addEducationView(Education education) {
        String degree = (education.getDegreeName() != null) ? education.getDegreeName() : "N/A";
        String specialization = (education.getSpecialisation() != null) ? education.getSpecialisation() : "N/A";
        String yearOfPassing = (education.getMonthYearOfPassing() != null) ? education.getMonthYearOfPassing() : "N/A";
        String college = (education.getCollegeName() != null) ? education.getCollegeName() : "N/A";
        String degreeType = (education.getDegreeType() != null) ? education.getDegreeType() : "N/A";

        TextView educationView = new TextView(this);
        educationView.setText("Degree: " + degree +
                "\nSpecialization: " + specialization +
                "\nYear of Passing: " + yearOfPassing +
                "\nCollege: " + college +
                "\nDegree Type: " + degreeType);

        educationView.setPadding(16, 16, 16, 16);
        educationView.setTextSize(16);
        educationView.setBackgroundResource(android.R.color.background_light);
        educationView.setTextColor(getResources().getColor(android.R.color.black));
        educationLayout.addView(educationView);
    }


    private void fetchExperienceDetails() {
        databaseReference.child("Experience").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot experienceSnapshot : dataSnapshot.getChildren()) {
                    Experience experience = experienceSnapshot.getValue(Experience.class);
                    if (experience != null) {
                        addExperienceView(experience);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewCVActivity.this, "Failed to load education details.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @SuppressLint("SetTextI18n")
    private void addExperienceView(Experience experience) {
        TextView experienceView = new TextView(this);
        experienceView.setText("Experience Type: " + experience.getExperienceType() +
                "\nJob Title: " + experience.getJobTitle() +
                "\nYear of Experience: " + experience.getYearsOfExperience() +
                "\nDescription: " + experience.getDescription());

        experienceView.setPadding(16, 16, 16, 16);
        experienceView.setTextSize(16);
        experienceView.setBackgroundResource(android.R.color.background_light);
        experienceView.setTextColor(getResources().getColor(android.R.color.black));
        experienceLayout.addView(experienceView);
    }

    private void fetchAwards() {
        databaseReference.child("Awards").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot awardSnapshot : dataSnapshot.getChildren()) {
                    Award award = awardSnapshot.getValue(Award.class);
                    if (award != null) {
                        addAwardView(award);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewCVActivity.this, "Failed to load awards.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addAwardView(Award award) {
        TextView awardView = new TextView(this);
        awardView.setText("Award Name: " + award.getAwardName() +
                "\nSponsoringAgency: " + award.getSponsoringAgency() +
                "\nPurpose: " + award.getPurpose() +
                "\nMonth: " + award.getMonth() +
                "\nYear: " + award.getYear() +
                "\nType of Award: " + award.getType());

        awardView.setPadding(16, 16, 16, 16);
        awardView.setTextSize(16);
        awardView.setBackgroundResource(android.R.color.background_light);
        awardView.setTextColor(getResources().getColor(android.R.color.black));
        awardsLayout.addView(awardView);
    }

    private void fetchPhDs() {
        databaseReference.child("PhDDetails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot phdSnapshot : dataSnapshot.getChildren()) {
                    PhDDetails phd = phdSnapshot.getValue(PhDDetails.class);
                    if (phd != null) {
                        addPhDView(phd);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewCVActivity.this, "Failed to load PhD details.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addPhDView(PhDDetails phd) {
        TextView phdView = new TextView(this);
        phdView.setText("Scholar Name: " + phd.getScholarName() +
                "\nRegistration No: " + phd.getRegNo() +
                "\nRegistration Date: " + phd.getRegDate() +
                "\nSubmission Date: " + phd.getSubmissionDate() +
                "\nViva Date: " + phd.getVivaDate() +
                "\nDegree Type: " + phd.getDegreeType() +
                "\nMode of Degree: " + phd.getModeOfDegree() +
                "\nStatus: " + phd.getStatus());

        phdView.setPadding(16, 16, 16, 16);
        phdView.setTextSize(16);
        phdView.setBackgroundResource(android.R.color.background_light);
        phdView.setTextColor(getResources().getColor(android.R.color.black));
        phdLayout.addView(phdView);
    }

    // Methods for the new sections
    private void fetchBooksDetails() {
        databaseReference.child("Books").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot bookSnapshot : dataSnapshot.getChildren()) {
                    Book book = bookSnapshot.getValue(Book.class);
                    if (book != null) {
                        addBookView(book);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewCVActivity.this, "Failed to load books.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addBookView(Book book) {
        TextView bookView = new TextView(this);
        bookView.setText("Book Title: " + book.getTitle() +
                "\nPublisher Name: " + book.getPublisher() +
                "\nMonth & Year: " + book.getMonthYear() +
                "\nEdition: " + book.getEdition() +
                "\nISBN No: " + book.getIsbnNo() +
                "\nNo of Pages: " + book.getNoOfPages() +
                "\nAuthors: " + book.getAuthor1() +" "+ book.getAuthor2() + " "+  book.getAuthor3());

        bookView.setPadding(16, 16, 16, 16);
        bookView.setTextSize(16);
        bookView.setBackgroundResource(android.R.color.background_light);
        bookView.setTextColor(getResources().getColor(android.R.color.black));
        bookLayout.addView(bookView);
    }

    private void fetchBookChapters() {
        databaseReference.child("BookChapters").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot chapterSnapshot : dataSnapshot.getChildren()) {
                    BookChapter chapter = chapterSnapshot.getValue(BookChapter.class);
                    if (chapter != null) {
                        addBookChapterView(chapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewCVActivity.this, "Failed to load book chapters.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addBookChapterView(BookChapter chapter) {
        TextView chapterView = new TextView(this);
        chapterView.setText("Book Title: " + chapter.getTitle() +
                "\nPublisher Name: " + chapter.getPublisher() +
                "\nMonth & Year: " + chapter.getMonthYear() +
                "\nChapter Number: " + chapter.getChapterNumber() +
                "\nEdition: " + chapter.getEdition() +
                "\nISBN No: " + chapter.getIsbn() +
                "\nNo of Pages: " + chapter.getPages() +
                "\nAuthors: " + chapter.getAuthor1() +" " + chapter.getAuthor2() +" "+chapter.getAuthor3());

        chapterView.setPadding(16, 16, 16, 16);
        chapterView.setTextSize(16);
        chapterView.setBackgroundResource(android.R.color.background_light);
        chapterView.setTextColor(getResources().getColor(android.R.color.black));
        bookChapterLayout.addView(chapterView);
    }

    private void fetchJournals() {
        databaseReference.child("Journals").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot journalSnapshot : dataSnapshot.getChildren()) {
                    Journal journal = journalSnapshot.getValue(Journal.class);
                    if (journal != null) {
                        addJournalView(journal);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewCVActivity.this, "Failed to load journals.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addJournalView(Journal journal) {
        TextView journalView = new TextView(this);
        journalView.setText(" Article Title: " + journal.getArticleTitle() +
                "\nPublisher Name: " + journal.getPubName() +
                "\nMonth & Year: " + journal.getMonthYear() +
                "\nVolume Number: " + journal.getVolumeNumber() +
                "\nEdition: " + journal.getEdition() +
                "\nISBN No: " + journal.getIsbnNo() +
                "\nNo of Pages: " + journal.getNoOfPages() +
                "\nAuthors: " + journal.getAuthor1() + " " + journal.getAuthor2() +" "+ journal.getAuthor3());

        journalView.setPadding(16, 16, 16, 16);
        journalView.setTextSize(16);
        journalView.setBackgroundResource(android.R.color.background_light);
        journalView.setTextColor(getResources().getColor(android.R.color.black));
        journalLayout.addView(journalView);
    }

    private void fetchConferences() {
        databaseReference.child("ConferenceArticles").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot paperSnapshot : dataSnapshot.getChildren()) {
                    Conference paper = paperSnapshot.getValue(Conference.class);
                    if (paper != null) {
                        addConferencePaperView(paper);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewCVActivity.this, "Failed to load conference papers.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addConferencePaperView(Conference paper) {
        TextView paperView = new TextView(this);
        paperView.setText("Title: " + paper.getTitle() +
                "\nConference Name: " + paper.getConferenceName() +
                "\nPublisher Name: " + paper.getPublisherName() +
                "\nMonth & Year: " + paper.getMonthYear() +
                "\nRenewal: " + paper.getRenewal() +
                "\nNo of Pages: " + paper.getNoOfPages());

        paperView.setPadding(16, 16, 16, 16);
        paperView.setTextSize(16);
        paperView.setBackgroundResource(android.R.color.background_light);
        paperView.setTextColor(getResources().getColor(android.R.color.black));
        conferencePaperLayout.addView(paperView);
    }

    private void fetchResearchInterests() {
        databaseReference.child("research_interests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot researchInterestSnapshot : dataSnapshot.getChildren()) {
                    ResearchInterest researchInterest = researchInterestSnapshot.getValue(ResearchInterest.class);
                    if (researchInterest != null) {
                        addResearchInterestView(researchInterest);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewCVActivity.this, "Failed to load research interests.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addResearchInterestView(ResearchInterest researchInterest) {
        TextView researchInterestView = new TextView(this);
        researchInterestView.setText("Title: " + researchInterest.getTitle());

        researchInterestView.setPadding(16, 16, 16, 16);
        researchInterestView.setTextSize(16);
        researchInterestView.setBackgroundResource(android.R.color.background_light);
        researchInterestView.setTextColor(getResources().getColor(android.R.color.black));
        researchInterestLayout.addView(researchInterestView);
    }

    private void fetchConsultancies() {
        databaseReference.child("Consultancies").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot consultancySnapshot : dataSnapshot.getChildren()) {
                    Consultancy consultancy = consultancySnapshot.getValue(Consultancy.class);
                    if (consultancy != null) {
                        addConsultancyView(consultancy);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewCVActivity.this, "Failed to load consultancies.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addConsultancyView(Consultancy consultancy) {
        TextView consultancyView = new TextView(this);
        consultancyView.setText("Project Title: " + consultancy.getProjectTitle() +
                "\nType of Project: " + consultancy.getTypeOfProject() +
                "\nSponsoring Agency: " + consultancy.getSponsoringAgency() +
                "\nAmount: " + consultancy.getAmount() +
                "\nStart Date: " + consultancy.getStartDate() +
                "\nEnd Date: " + consultancy.getEndDate() +
                "\nYear of Completion: " + consultancy.getYearOfCompletion() +
                "\nStatus: " + consultancy.getStatus());

        consultancyView.setPadding(16, 16, 16, 16);
        consultancyView.setTextSize(16);
        consultancyView.setBackgroundResource(android.R.color.background_light);
        consultancyView.setTextColor(getResources().getColor(android.R.color.black));
        consultancyLayout.addView(consultancyView);
    }

    private void fetchPatents() {
        databaseReference.child("Patents").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot patentSnapshot : dataSnapshot.getChildren()) {
                    Patent patent = patentSnapshot.getValue(Patent.class);
                    if (patent != null) {
                        addPatentView(patent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewCVActivity.this, "Failed to load patents.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addPatentView(Patent patent) {
        TextView patentView = new TextView(this);
        patentView.setText("Patent Title: " + patent.getTitle() +
                "\nPurpose: " + patent.getPurpose() +
                "\nFiled On: " + patent.getFiledOn() +
                "\nPublished Date: " + patent.getPublishedDate() +
                "\nAuthors: " + patent.getAuthor1() + patent.getAuthor2());

        patentView.setPadding(16, 16, 16, 16);
        patentView.setTextSize(16);
        patentView.setBackgroundResource(android.R.color.background_light);
        patentView.setTextColor(getResources().getColor(android.R.color.black));
        patentLayout.addView(patentView);
    }

    private void fetchMembershipDetails() {
        databaseReference.child("memberships").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot memberSnapshot : dataSnapshot.getChildren()) {
                    Membership member = memberSnapshot.getValue(Membership.class);
                    if (member != null) {
                        addMemberView(member);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewCVActivity.this, "Failed to load patents.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addMemberView(Membership member) {
        TextView memberView = new TextView(this);
        memberView.setText("Member Type: " + member.getMemberType() +
                "\nYear: " + member.getYear() +
                "\nDescription: " + member.getDescription());

        memberView.setPadding(16, 16, 16, 16);
        memberView.setTextSize(16);
        memberView.setBackgroundResource(android.R.color.background_light);
        memberView.setTextColor(getResources().getColor(android.R.color.black));
        memberLayout.addView(memberView);
    }

    private void fetchPrograms() {
        databaseReference.child("ProgramsAttended").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                programLayout.removeAllViews(); // Clear previous views
                for (DataSnapshot programSnapshot : dataSnapshot.getChildren()) {
                    Program program = programSnapshot.getValue(Program.class);
                    if (program != null) {
                        addProgramView(program); // Add each program to the layout
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewCVActivity.this, "Failed to load programs.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addProgramView(Program program) {
        // Create a TextView for each program entry
        TextView programView = new TextView(this);
        programView.setText("Title: " + program.getTitle() +
                "\nOrganized By: " + program.getOrganizedBy() +
                "\nPurpose: " + program.getPurpose() +
                "\nStart Date: " + program.getStartDate() +
                "\nEnd Date: " + program.getEndDate() +
                "\nPlace: " + program.getPlace() +
                "\nProgram Type: " + program.getProgramType());

        // Optionally, style the TextView
        programView.setPadding(16, 16, 16, 16);
        programView.setTextSize(16);
        programView.setBackgroundResource(android.R.color.background_light);
        programView.setTextColor(getResources().getColor(android.R.color.black));
        programLayout.addView(programView);

    }

    private void fetchInvitedLectures() {
        databaseReference.child("InvitedLectures").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lecturesLayout.removeAllViews(); // Clear previous views
                for (DataSnapshot lectureSnapshot : dataSnapshot.getChildren()) {
                    InvitedLecture lecture = lectureSnapshot.getValue(InvitedLecture.class);
                    if (lecture != null) {
                        addLectureView(lecture);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewCVActivity.this, "Failed to load invited lectures.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addLectureView(InvitedLecture lecture) {
        // Create a TextView for each invited lecture entry
        TextView lectureView = new TextView(this);
        lectureView.setText("Lecture Title: " + lecture.getTitle() +
                "\nPurpose: " + lecture.getPurpose() +
                "\nOrganized By: " + lecture.getOrganizedBy() +
                "\nStart Date: " + lecture.getStartDate() +
                "\nEnd Date: " + lecture.getEndDate() +
                "\nPlace: " + lecture.getPlace());

        // Optionally, style the TextView
        lectureView.setPadding(16, 16, 16, 16);
        lectureView.setTextSize(16);
        lectureView.setBackgroundResource(android.R.color.background_light);
        lectureView.setTextColor(getResources().getColor(android.R.color.black));

        // Add the TextView to the layout
        lecturesLayout.addView(lectureView);
    }

    private void fetchCitationDetails() {
        databaseReference.child("Citations").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                citationLayout.removeAllViews(); // Clear existing views to avoid duplication

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Fetch label (key) and URL (value)
                    String label = snapshot.getKey();
                    String url = snapshot.getValue(String.class);

                    if (label != null && url != null) {
                        addCitationToLayout(label, url);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(ViewCVActivity.this, "Error fetching data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Database error: " + databaseError.getMessage());
            }
        });
    }

    private void addCitationToLayout(String label, String url) {
        // Create a TextView to display the label and link
        TextView textView = new TextView(this);
        textView.setText(label + ": " + url);

        // Optionally, set additional properties for better display (e.g., padding, text color)
        textView.setPadding(16, 16, 16, 16);
        textView.setTextSize(16);
        textView.setBackgroundResource(android.R.color.background_light);
        textView.setTextColor(getResources().getColor(android.R.color.black));

        // Add the TextView to the layout
        citationLayout.addView(textView);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with downloading
                createAndSharePDF();
            } else {
                // Permission denied, show a message
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Bitmap getBitmapFromView(View view) {
        // Create a bitmap with the same dimensions as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }

    private void createAndSharePDF() {
        // Capture the View Full CV layout as bitmap
        View cvView = findViewById(R.layout.activity_view_cv); // Replace with your layout's ID
        Bitmap bitmap = getBitmapFromView(cvView);

        // Create a new PDF document
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        // Draw the bitmap to the PDF page
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        canvas.drawBitmap(bitmap, 0, 0, paint);
        pdfDocument.finishPage(page);

        // Save the document to a file
        File pdfFile = new File(getExternalFilesDir(null), "resume.pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(pdfFile));
            Toast.makeText(this, "PDF created successfully!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // Close the document
        pdfDocument.close();

        // Now share the PDF
        sharePDF(pdfFile);
    }

    private void sharePDF(File file) {
        Uri pdfUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", file);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_STREAM, pdfUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // Show the chooser dialog
        startActivity(Intent.createChooser(intent, "Share CV PDF"));
    }



}

