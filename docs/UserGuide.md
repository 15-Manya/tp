---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# TeamEventPro User Guide

TeamEventPro is a **desktop app for managing tech meetup attendees**, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, TeamEventPro can get your event management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Release Notes (MVP)

**TeamEventPro (MVP)** is a keyboard-first desktop app built for small tech meetup organizers in Singapore to manage attendee information quickly during live events.

This MVP focuses on the core workflow of tracking participants from registration to attendance: add and edit applicant profiles, search and filter records, mark check-ins, and keep the metadata in one place. Each profile supports key fields such as name, phone, email, address, tags, team, GitHub username, check-in status and RSVP status.

### New Features

**Check-In Command** — `checkin INDEX`
* Marks a person as checked-in based on their position in the latest displayed list. INDEX refers to the number shown in the most recent list of people.

![Check-in example](https://github.com/user-attachments/assets/e4502cd5-b115-4b5c-bc35-17452286f4ca)
![Check-in example](https://github.com/user-attachments/assets/7695ac00-baf3-44d5-ae66-6da8f93d8ef3)
![Check-in example](https://github.com/user-attachments/assets/50b1cc6f-be1e-43bf-bf71-211ddecb72b3)

**Filter Command** — `filter PREFIX/VALUE`
* Narrows the applicant list by either RSVP status (`r/`) or tag (`t/`). Supports filtering by exactly one criterion per command.

![Filter example](https://github.com/user-attachments/assets/3ae4aec7-23ad-4d7e-a367-b2ac393efc5f)

**Assign Team Command** — `assign INDEX team/TEAM_NAME`
* Assigns a participant to a team. TEAM_NAME must be alphanumeric and up to 15 characters.

![Assign example](https://github.com/user-attachments/assets/0ff09a1a-5041-4974-a0d0-9e6481b7fbb1)

### Modified Features

**Add Command** — Added support for GitHub username (`g/`) and RSVP status (`r/`) under a person's information.

![Add example](https://github.com/user-attachments/assets/74e1d500-6387-4a69-a45f-5f30fa6e29a5)

**Search Command** — Renamed from `find`. Added support for searching by email and GitHub username. Email and GitHub username matching use substring matching.

![Search example](https://github.com/user-attachments/assets/b8c6b06b-ad54-468f-8d72-309964a8388b)

**Edit Command** — Renamed from `modify`. Updates an applicant using their list index. Allows changing one or more fields in one command: name, phone, email, address, team, GitHub username, RSVP, or tags.

![Edit example](https://github.com/user-attachments/assets/5953dbd3-bf57-44ee-ba56-13f63b2fba17)

### Unmodified Features

**Delete Command** — Deletes a person based on their position in the latest displayed list.

![Delete example](https://github.com/user-attachments/assets/c3a85a7b-9e5d-4e89-99e0-0312f6c0a11a)

**List Command** — Lists all the people in the directory.

![List example](https://github.com/user-attachments/assets/456fa1f7-2750-4ad4-980f-7269a6e1bc8f)

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed on your computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `.jar` file from [Releases](https://github.com/AY2526S2-CS2103T-W11-1/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for TeamEventPro.

4. Open a command terminal, `cd` into the folder containing the jar file, and run `java -jar addressbook.jar`.<br>
   A GUI similar to the below should appear in a few seconds. <br>
   ![Ui](images/Ui.png)

5. Type commands in the command box and press Enter to execute them. For example, typing **`help`** and pressing Enter opens the help window.<br>
   Some example commands you can try:

   * `list` : Lists all applicants.
   * `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 tm/Dev g/johndoe r/yes` : Adds an applicant with team, GitHub, and RSVP status.
   * `filter r/yes` : Filters to show only applicants who have RSVP'd yes.
   * `checkin 1` : Marks the 1st applicant in the current list as checked in.
   * `assign 2 team/Alpha` : Assigns the 2nd applicant to team Alpha.
   * `delete 3` : Deletes the 3rd applicant shown in the current list.
   * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g. `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/colleague` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

Format: `help`

### Adding an applicant: `add`

Adds an applicant to the address book. Supports GitHub username and RSVP status in addition to core contact fields.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [tm/TEAM] [g/GITHUB_USERNAME] [r/RSVP_STATUS] [t/TAG]…​`

* `RSVP_STATUS` must be `yes`, `no`, or `pending` (case-insensitive).
* `TEAM` must be alphanumeric and at most 15 characters.

<box type="tip" seamless>

**Tip:** An applicant can have any number of tags (including 0)
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25`
* `add n/Betsy Crowe p/1234567 e/betsy@example.com a/Newgate Prison tm/Development g/betsycrowe r/yes t/Python t/ML`

### Listing all applicants : `list`

Lists all the people in the directory.

Format: `list`

### Editing an applicant : `edit`

Edits an existing applicant in the address book. (Renamed from `modify`.) Updates an applicant using their list index; you can change one or more fields in a single command.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [tm/TEAM] [g/GITHUB_USERNAME] [r/RSVP_STATUS] [t/TAG]…​`

* Edits the applicant at the specified `INDEX`. The index refers to the index number shown in the displayed applicant list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be overwritten by the input values.
* When editing tags, the existing tags of the applicant will be replaced (tags are not cumulative).
* You can remove all the applicant's tags by typing `t/` without specifying any tags after it.
* You can clear the team by typing `tm/` with nothing after it.

Examples:
* `edit 1 p/91234567 e/johndoe@example.com` — Edits the phone number and email of the 1st applicant.
* `edit 2 n/Betsy Crower t/` — Edits the name to `Betsy Crower` and clears all existing tags.
* `edit 3 r/yes tm/Alpha` — Updates RSVP to yes and assigns the applicant to team Alpha.

### Assigning a team : `assign`

Assigns an applicant to a specific hackathon team.

Format: `assign INDEX team/TEAM_NAME`

* The index refers to the index number shown in the displayed applicant list.
* The index **must be a positive integer** 1, 2, 3, …​
* `TEAM_NAME` must be alphanumeric and at most 15 characters.

Examples:
* `assign 2 team/Alpha`
* `assign 1 team/Team7`

### Filtering applicants : `filter`

Narrows the applicant list by a single criterion—either RSVP status or tag. You cannot filter by both at once.

Format: `filter r/RSVP_STATUS` or `filter t/TAG`

* `RSVP_STATUS` must be `yes`, `no`, or `pending` (case-insensitive).
* Use exactly one criterion per command.

Examples:
* `filter r/yes` — Shows only applicants who have RSVP'd yes.
* `filter r/pending` — Shows applicants with pending RSVP.
* `filter t/Python` — Shows applicants tagged with Python.

### Checking in an applicant : `checkin`

Marks a person as checked-in based on their position in the latest displayed list.

Format: `checkin INDEX`

* The index refers to the index number shown in the displayed applicant list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `checkin 1` — Checks in the 1st applicant in the list.
* `filter r/yes` followed by `checkin 2` — Checks in the 2nd applicant from the filtered results.

### Locating applicants by name, email, or GitHub username : `search`

Finds applicants whose names, emails, or GitHub usernames match any of the given keywords. (Renamed from `find`.) Supports searching by email and GitHub username; email and GitHub matching use substring matching.

Format: `search KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g. `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Names, emails, and GitHub usernames are searched.
* Names use full-word matching e.g. `Han` will not match `Hans`
* Email and GitHub username matches use case-insensitive substring matching.
* Applicants matching at least one keyword will be returned (i.e. `OR` search).

Examples:
* `search John` — Returns applicants named John
* `search alexyeoh@example.com lidavid` — Returns applicants matching the email or GitHub username

### Deleting an applicant : `delete`

Deletes a person based on their position in the latest displayed list.

Format: `delete INDEX`

* Deletes the applicant at the specified `INDEX`.
* The index refers to the index number shown in the displayed applicant list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` — Deletes the 2nd applicant in the address book.
* `search Betsy` followed by `delete 1` — Deletes the 1st applicant in the search results.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

TeamEventPro data are saved to the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

TeamEventPro data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users can update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file make its format invalid, TeamEventPro will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause TeamEventPro to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: Install the app on the other computer and overwrite the empty data file it creates with the file that contains your previous TeamEventPro data.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI may open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action | Format, Examples |
|--------|------------------|
| **Add** | `add n/NAME p/PHONE e/EMAIL a/ADDRESS [tm/TEAM] [g/GITHUB] [r/RSVP] [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd tm/Dev g/jamesho r/yes t/Python` |
| **Assign** | `assign INDEX team/TEAM_NAME` <br> e.g., `assign 2 team/Alpha` |
| **CheckIn** | `checkin INDEX` <br> e.g., `checkin 1` |
| **Clear** | `clear` |
| **Delete** | `delete INDEX` <br> e.g., `delete 3` |
| **Edit** | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [tm/TEAM] [g/GITHUB] [r/RSVP] [t/TAG]…​` <br> e.g., `edit 2 n/James Lee e/jameslee@example.com` |
| **Filter** | `filter r/RSVP` or `filter t/TAG` <br> e.g., `filter r/yes`, `filter t/Python` |
| **List** | `list` |
| **Search** | `search KEYWORD [MORE_KEYWORDS]` <br> e.g., `search James Jake` |
| **Help** | `help` |
| **Exit** | `exit` |
