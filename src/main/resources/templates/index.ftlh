<#import "common/page.ftlh" as c>
<#import "common/navbar.ftlh" as nav>

<@c.page>

    <@nav.nav>
    </@nav.nav>
    <#assign specializations = ['Therapist', 'Cardiologist', 'Dermatologist', 'Gynecologist', 'Ophthalmologist',
    'Orthopedist', 'Psychiatrist', 'Surgeon', 'Endocrinologist', 'Nephrologist', 'Gastroenterologist']>
    <#if !specialization??>
        <#assign specialization = "">
    </#if>
    <div style="width: 96%; margin: auto; padding: 10px;">
        <form action="/" class="form-inline my-2 my-lg-0" style="width: fit-content; margin: auto">
            <select class="form-control" id="specialization" name="specialization">
                <#list specializations as s>
                    <option value="${s}" <#if ( specialization == s)>selected="selected"</#if>>${s}</option>
                </#list>
                <option value="" <#if (specialization == "")>selected="selected"</#if>>Show all</option>
            </select>
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
        <div class="list" style="display: flex; flex-wrap: wrap; justify-content: center; width: fit-content">

            <#list doctors as doctor>
                <div class="card"
                     style="width: 16.8rem;  margin-top: 10px; margin-right: 10px; padding-bottom: 40px;">

                    <div class="card-body">
                        <h5 class="card-title">${doctor.name}</h5>
                        <p class="card-text">${doctor.specialization}</p>
                        <p class="card-text font-weight-light">
                            Cost of the first appointment: ${doctor.price}$</p>
                        <div style="display: flex; flex-wrap: wrap; column-gap: 4px; row-gap: 4px">
                            <#list doctor.getDates() as appointment>
                                <a style="display: block;" href="/doctor/${doctor.id?c}"
                                   class="btn btn-primary" type="submit">${appointment}</a>
                            <#else>
                                There are no dates available to make an appointment with a doctor
                            </#list>
                        </div>
                    </div>
                </div>
            <#else>
                <h3>Doctors not found</h3>
            </#list>
        </div>
    </div>





    </script>

</@c.page>